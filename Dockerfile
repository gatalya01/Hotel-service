FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests=true

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/hotel-booking-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
