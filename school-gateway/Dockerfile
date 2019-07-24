FROM adoptopenjdk/openjdk11:slim
ARG JAR_FILE
ADD ${JAR_FILE} /usr/app/school-gateway.jar
WORKDIR /usr/app
EXPOSE 8180
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","school-gateway.jar"]
