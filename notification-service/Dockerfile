# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy vocab-dto and build it
COPY ../vocab-dto ./vocab-dto
RUN cd vocab-dto && mvn clean install -DskipTests

# Copy notification-service and build it
COPY . ./notification-service
WORKDIR /build/notification-service
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /build/notification-service/target/notification-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
