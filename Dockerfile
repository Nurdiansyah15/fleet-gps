# Build stage
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR from builder
COPY --from=builder /workspace/app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variables (can be overridden in docker-compose)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/fleet_gps
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

ENTRYPOINT ["java", "-jar", "app.jar"]