# ==========================
# Stage 1: Build Application
# ==========================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy everything from project
COPY . .

# Give execute permission to Maven Wrapper
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests


# ==========================
# Stage 2: Run Application
# ==========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup

# Copy generated jar
COPY --from=builder /app/target/*.jar app.jar

# Change ownership
RUN chown appuser:appgroup app.jar

# Run as non-root user
USER appuser

# Render provides PORT env variable
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]