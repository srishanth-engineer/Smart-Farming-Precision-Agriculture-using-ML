# --- Stage 1: Build the Java Application ---
FROM maven:3.9-eclipse-temurin-23 AS build

# Set working directory
WORKDIR /app

# Copy Maven files to leverage caching
COPY pom.xml ./

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY . .

# Build the application without running tests
RUN mvn clean package -DskipTests

# --- Stage 2: Create the final lightweight image ---
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/SpringLoginPage-0.0.1-SNAPSHOT.jar app.jar

# Expose backend port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
