# === Stage 1: Build the JAR ===
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Set working directory inside the build container
WORKDIR /app

# Copy the project files to the build container
COPY . .

# Build the Spring Boot project and skip tests for faster build
RUN mvn clean package -DskipTests

# === Stage 2: Run the app using a lightweight image ===
FROM openjdk:17-jdk-slim

# Set working directory in runtime container
WORKDIR /app

# Copy the built jar file from the build container
COPY --from=build /app/target/InventoryManagement-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]

