package com.admissioncrm.apigateway.FIlter;


import com.admissioncrm.apigateway.Utills.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Paths that don't require authentication
    private final String[] PUBLIC_PATHS = {
            "/api/auth/login",
            "/api/auth/s/register",
            "/api/auth/otp/**",
            "/actuator/**",
            "/favicon.ico"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        String method = request.getMethod();

        logger.debug("Processing request: {} {}", method, requestPath);

        // Skip authentication for public paths
        if (isPublicPath(requestPath)) {
            logger.debug("Public path detected, skipping authentication: {}", requestPath);
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = jwtUtil.extractTokenFromHeader(authHeader);

        if (token == null) {
            logger.warn("No JWT token found in request to protected path: {}", requestPath);
            sendUnauthorizedResponse(response, "Access token is required");
            return;
        }

        try {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                List<String> roles = jwtUtil.getRolesFromToken(token);

                logger.debug("JWT validated for user: {} with roles: {}", username, roles);

                // Convert roles to authorities
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                // Create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // Add user info to request headers for downstream services
                addUserInfoToHeaders(request, response,  username, roles, chain);

//                chain.doFilter(request, response);
            } else {
                logger.warn("Invalid JWT token for path: {}", requestPath);
                sendUnauthorizedResponse(response, "Invalid or expired access token");
            }
        } catch (Exception e) {
            logger.error("JWT authentication failed: {}", e.getMessage(), e);
            sendUnauthorizedResponse(response, "Authentication failed");
        }
    }

    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (pathMatcher.match(publicPath, path)) {
                return true;
            }
        }
        return false;
    }
    private void addUserInfoToHeaders(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String username,
                                      List<String> roles,
                                      FilterChain chain) throws IOException, ServletException {

        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("X-Username", username);
        extraHeaders.put("X-User-Roles", String.join(",", roles));

        HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                String headerValue = extraHeaders.get(name);
                if (headerValue != null) {
                    return headerValue;
                }
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if (extraHeaders.containsKey(name)) {
                    return Collections.enumeration(List.of(extraHeaders.get(name)));
                }
                return super.getHeaders(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                List<String> names = Collections.list(super.getHeaderNames());
                names.addAll(extraHeaders.keySet());
                return Collections.enumeration(names);
            }
        };

        chain.doFilter(wrappedRequest, response);
    }


    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", message);
        errorResponse.put("status", 401);
        errorResponse.put("timestamp", System.currentTimeMillis());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}