FROM openjdk:17-alpine
WORKDIR /opt
ENV PORT 8012
EXPOSE 8012
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar