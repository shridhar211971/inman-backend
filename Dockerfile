# === Stage 1: Build the Spring Boot app ===
FROM maven:3.8.1-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (caches layer)
COPY pom.xml .

RUN mvn dependency:go-offline

# Copy the rest of the application source code
COPY . .

# Build the Spring Boot app (skip tests for faster builds)
RUN mvn clean package -DskipTests

# === Stage 2: Run the app with JDK only ===
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
