
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./auth/pom.xml auth/
COPY ./auth/src/ auth/src/

WORKDIR /app/auth
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/auth/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]