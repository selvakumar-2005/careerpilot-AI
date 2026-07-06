# =============================================
# CareerPilot AI - Backend Dockerfile
# Build context: repo root
# =============================================

# Stage 1: Build the JAR with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY careerpilot-backend/pom.xml .
RUN mvn dependency:go-offline -q
COPY careerpilot-backend/src ./src
RUN mvn clean package -DskipTests -q

# Stage 2: Run with lightweight JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/careerpilot-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
