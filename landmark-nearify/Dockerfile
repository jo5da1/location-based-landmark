# -------- Build Stage --------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom and download dependencies first (better layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Run Stage --------
#FROM eclipse-temurin:21-jdk-alpine
FROM eclipse-temurin:21-jre-alpine


WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","app.jar"]