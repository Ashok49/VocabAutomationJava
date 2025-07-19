# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy vocab-dto and build it
COPY ../vocab-dto ./vocab-dto
RUN cd vocab-dto && mvn clean install -DskipTests

# Copy vocab-service-2 and build it
COPY . ./vocab-service-2
WORKDIR /build/vocab-service-2
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /build/vocab-service-2/target/vocab-automation-java-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]