FROM openjdk:17-alpine
#WORKDIR /opt
ENV PORT 9999
EXPOSE 9999
COPY target/backoffice-webapi-1.0-SNAPSHOT.jar backoffice.jar
ENTRYPOINT ["java","-jar","/backoffice.jar"]