# Use official JDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Pre-download dependencies (better caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Package the app
RUN ./mvnw clean package -DskipTests

# Copy the built jar into container
COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
