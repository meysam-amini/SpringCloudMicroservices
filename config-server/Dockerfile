FROM openjdk:17-alpine
#WORKDIR /opt
ENV PORT 8012
EXPOSE 8012
COPY target/config-server-1.0-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java","-jar","config-server.jar"]