# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy vocab-dto and build it (relative to build context, so go up one level)
COPY ../vocab-dto /build/vocab-dto
RUN cd vocab-dto && mvn clean install -DskipTests

# Copy only the contents of notification-service (build context is notification-service)
COPY . /build
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /build/target/notification-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
