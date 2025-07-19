# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy and build vocab-dto first
COPY ../vocab-dto /app/vocab-dto
RUN cd vocab-dto && mvn clean install -DskipTests

# Copy notification-service source and build it
COPY . /app/notification-service
WORKDIR /app/notification-service
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/notification-service/target/notification-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

