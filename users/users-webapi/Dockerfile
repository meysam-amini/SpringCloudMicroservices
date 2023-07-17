FROM openjdk:17-alpine
WORKDIR /opt
ENV PORT 3333
EXPOSE 3333
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar