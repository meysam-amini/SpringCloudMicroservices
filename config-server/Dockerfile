FROM openjdk:17-alpine
#WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY target/config-server-1.0-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java","-jar","config-server.jar"]