FROM openjdk:17-alpine
WORKDIR /opt
ENV PORT 4444
EXPOSE 4444
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar