FROM adoptopenjdk/openjdk11:slim
ARG JAR_FILE
ADD ./build/libs/school-gateway-1.2.jar /usr/app/school-gateway.jar
WORKDIR /usr/app
EXPOSE 8180
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","school-gateway.jar"]
