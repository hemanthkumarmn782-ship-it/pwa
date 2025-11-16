# ------------ Build Stage ------------
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn -q -e dependency:go-offline

# Copy source code
COPY src ./src
COPY mvnw .
COPY .mvn .mvn

# Build the application
RUN mvn -e -DskipTests package

# ------------ Run Stage ------------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
