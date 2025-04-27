
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app
COPY ./gateway/pom.xml gateway/
COPY ./gateway/src/ gateway/src/

WORKDIR /app/gateway
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/gateway/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]