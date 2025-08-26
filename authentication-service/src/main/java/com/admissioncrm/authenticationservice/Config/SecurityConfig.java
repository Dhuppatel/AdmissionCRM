package com.admissioncrm.authenticationservice.Config;




import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // enables @PreAuthorize and @PostAuthorize
public class SecurityConfig {
    @Autowired
     private GatewayAuthenticationFilter gatewayAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(gatewayAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}








//
//import com.admissioncrm.authenticationservice.Services.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//@EnableMethodSecurity
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    JwtFilter jwtFilter;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(AbstractHttpConfigurer::disable);
//
////        //enable CORS
////        http.cors(cors-> cors.configurationSource(corsConfigurationSource()));
//
//        http.authorizeHttpRequests(authorize -> {
//                // Public endpoints - no authentication (these are accessed directly, not through gateway)
//                authorize.requestMatchers(
//                        "/api/auth/login",
//                        "/api/auth/s/register",
//                        "/api/auth/request-password-reset",
//                        "/api/auth/reset-password",
//                        "/api/auth/verify-otp",
//                        "/actuator/**",
//                        //swagger endpoints
//                        "/swagger-ui/**",
//                        "/swagger-ui.html",
//                        "/v3/api-docs/**",
//                        "/swagger-resources/**",
//                        "/webjars/**"
//                ).permitAll();
//
//                // Role-based endpoints (trust gateway authentication)
//                authorize.requestMatchers("/api/auth/admin/**").hasAnyRole("UNIVERSITY_ADMIN", "INSTITUTE_ADMIN");
//                authorize.requestMatchers("/api/university-admin/**").hasRole("UNIVERSITY_ADMIN");
//                authorize.requestMatchers("/api/institute-admin/**").hasAnyRole("UNIVERSITY_ADMIN", "INSTITUTE_ADMIN");
//                authorize.requestMatchers("/api/counsellor/**").hasAnyRole("UNIVERSITY_ADMIN", "INSTITUTE_ADMIN");
//                authorize.requestMatchers("/api/student/**").hasRole("STUDENT");
//                authorize.requestMatchers("/api/auth/stats/**").hasAnyRole("UNIVERSITY_ADMIN", "INSTITUTE_ADMIN");
//
//                authorize.anyRequest().permitAll();
//            });
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//
//    }
//
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(List.of("http://localhost:4028",
////                                                "http://localhost:3000",
////                                                "http://localhost:5173")); //add Frontend URL's here aa
////        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
////        configuration.setAllowedHeaders(List.of("*"));
////        configuration.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//}
