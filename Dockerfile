# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Test-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Health check to ensure the application is running
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:8080 || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
