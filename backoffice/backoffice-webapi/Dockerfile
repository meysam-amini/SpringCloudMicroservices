# Stage 1: Build the Maven project
FROM maven:3.9.9-amazoncorretto-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the custom settings.xml for Maven to use the private repository
#COPY settings.xml /root/.m2/

# Pass the CI registry credentials as build arguments (these will come from environment variables)
#ARG CI_REGISTRY_USERNAME
#ARG CI_REGISTRY_PASSWORD

# Set the Maven repository credentials as environment variables
#ENV CI_REGISTRY_USERNAME=${CI_REGISTRY_USERNAME}
#ENV CI_REGISTRY_PASSWORD=${CI_REGISTRY_PASSWORD}

# Copy the pom.xml and download the dependencies (cached if no changes)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package --batch-mode --update-snapshots

# Stage 2: Create the final lightweight image
FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables for the Spring profile
#ENV SPRING_PROFILES_ACTIVE=docker

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
