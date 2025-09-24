# Step 1: Use a JDK base image
FROM openjdk:21-oracle

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy built jar from host
COPY target/*.jar app.jar

# Step 4: Expose port
EXPOSE 8080

# Step 5: Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
