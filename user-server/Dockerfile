FROM openjdk:17
MAINTAINER Desmond Kimutai <desmondkym.dk@gmail.com>
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8092:8092