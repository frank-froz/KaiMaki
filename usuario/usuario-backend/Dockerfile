# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
COPY src/main/resources/application.yml src/main/resources/application.yml
RUN mvn clean package -DskipTests

# Etapa 2: Runtime con JDK liviano
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
