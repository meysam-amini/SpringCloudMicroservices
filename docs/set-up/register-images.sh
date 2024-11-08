#!/bin/bash

# Set variables
REGISTRY_URL="163.5.94.91:5000"                   # Your Docker registry URL

# Image 1: Maven with OpenJDK 21 on Alpine
MAVEN_IMAGE_NAME="maven"    # Custom image name for Maven
MAVEN_TAG="jdk-21"            # Custom tag for Maven image
MAVEN_FULL_IMAGE_NAME="${REGISTRY_URL}/${MAVEN_IMAGE_NAME}:${MAVEN_TAG}"

# Image 2: OpenJDK 21 on Alpine
JDK_IMAGE_NAME="openjdk"             # Custom image name for OpenJDK
JDK_TAG="21"                            # Custom tag for OpenJDK image
JDK_FULL_IMAGE_NAME="${REGISTRY_URL}/${JDK_IMAGE_NAME}:${JDK_TAG}"

# Step 1: Create Dockerfile for Maven with OpenJDK 21
echo "Creating Dockerfile for ${MAVEN_FULL_IMAGE_NAME}..."
cat << 'EOF' > Dockerfile.maven
# Base image with OpenJDK 21 on Alpine
FROM eclipse-temurin:21-jdk-alpine as jdk

# Install Maven on top of the JDK
RUN apk update && \
    apk add --no-cache maven

# Verify installation
RUN java -version && mvn -version
EOF

# Step 2: Build and push Maven image
echo "Building Docker image ${MAVEN_FULL_IMAGE_NAME}..."
docker build -t ${MAVEN_FULL_IMAGE_NAME} -f Dockerfile.maven .
echo "Pushing Docker image ${MAVEN_FULL_IMAGE_NAME} to ${REGISTRY_URL}..."
docker push ${MAVEN_FULL_IMAGE_NAME}

# Step 3: Create Dockerfile for OpenJDK 21 on Alpine
echo "Creating Dockerfile for ${JDK_FULL_IMAGE_NAME}..."
cat << 'EOF' > Dockerfile.jdk
# Use Alpine base image with OpenJDK 21
FROM eclipse-temurin:21-jdk-alpine

# Verify Java installation
RUN java -version
EOF

# Step 4: Build and push OpenJDK image
echo "Building Docker image ${JDK_FULL_IMAGE_NAME}..."
docker build -t ${JDK_FULL_IMAGE_NAME} -f Dockerfile.jdk .
echo "Pushing Docker image ${JDK_FULL_IMAGE_NAME} to ${REGISTRY_URL}..."
docker push ${JDK_FULL_IMAGE_NAME}

# Step 5: Clean up Dockerfiles
rm Dockerfile.maven Dockerfile.jdk

echo "Docker images ${MAVEN_FULL_IMAGE_NAME} and ${JDK_FULL_IMAGE_NAME} built and pushed successfully."
