FROM openjdk:17-alpine
#WORKDIR /opt
ENV PORT 8082
EXPOSE 8082
COPY target/api-gateway-1.0-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]