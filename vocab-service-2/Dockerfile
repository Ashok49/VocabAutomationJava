# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy vocab-dto and build it (relative to build context, so go up one level)
COPY ../vocab-dto /build/vocab-dto
RUN cd vocab-dto && mvn clean install -DskipTests

# Copy only the contents of vocab-service-2 (build context is vocab-service-2)
COPY . /build
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /build/target/vocab-automation-java-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]