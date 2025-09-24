# Stage 1: Build the jar using Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the jar
FROM openjdk:21-oracle
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .env

EXPOSE 6002
ENTRYPOINT ["java", "-jar", "app.jar"]
