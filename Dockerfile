# Etapa 1: build con Gradle y JDK 21
FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /app

COPY . .
RUN chmod +x gradlew && ./gradlew clean build -x test --no-daemon

# Etapa 2: imagen liviana solo con el JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
