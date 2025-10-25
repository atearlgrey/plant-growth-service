# Build stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Add a non-root user
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser

# Copy the built artifact from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set ownership to non-root user
RUN chown -R javauser:javauser /app

# Switch to non-root user
USER javauser

# Configure JVM options for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Expose the application port
EXPOSE 8080

# Set the entry point
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]