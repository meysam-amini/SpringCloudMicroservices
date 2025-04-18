# Stage 1: Build the Maven project
FROM ${CI_REGISTRY}/maven:jdk-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the custom settings.xml for Maven to use the private repository (if needed)
COPY settings.xml /root/.m2/

# Pass the CI registry credentials as build arguments (these will come from environment variables)
ARG CI_REGISTRY_USERNAME
ARG CI_REGISTRY_PASSWORD

# Set the Maven repository credentials as environment variables (if needed)
ENV CI_REGISTRY_USERNAME=${CI_REGISTRY_USERNAME}
ENV CI_REGISTRY_PASSWORD=${CI_REGISTRY_PASSWORD}

# Copy the pom.xml and download the dependencies (cached if no changes)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package --batch-mode --update-snapshots

# Stage 2: Create the final lightweight image
FROM ${CI_REGISTRY}/openjdk:21
#FROM maven:3.8.6-eclipse-temurin-21

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables for the Spring profile
#ENV SPRING_PROFILES_ACTIVE=docker

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
