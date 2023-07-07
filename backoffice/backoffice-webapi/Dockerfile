FROM openjdk:17-alpine
WORKDIR /opt
ENV PORT 9999
EXPOSE 9999
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar