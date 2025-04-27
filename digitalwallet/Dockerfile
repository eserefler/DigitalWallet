
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./digitalwallet/pom.xml digitalwallet/
COPY ./digitalwallet/src/ digitalwallet/src/

WORKDIR /app/digitalwallet
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/digitalwallet/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]