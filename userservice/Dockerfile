FROM openjdk:11.0.3-jdk

LABEL maintainer="andrem911@mail.ru"

ARG JAR_FILE

ADD ${JAR_FILE} app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]