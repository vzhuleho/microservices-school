ARG JAVA_VERSION=11.0.3
#ARG APP_VERSION=0.0.1
#ARG APP_NAME=school-curriculum-$APP_VERSION.jar
#ARG APP_LOCAL_PATH=build/libs/$APP_NAME
#ARG APP_DOCKER_PATH=/usr/src/$APP_NAME

FROM openjdk:$JAVA_VERSION

COPY build/libs/school-curriculum-0.0.1.jar /usr/src/school-curriculum-0.0.1.jar
#COPY $APP_LOCAL_PATH $APP_DOCKER_PATH

CMD java -jar /usr/src/school-curriculum-0.0.1.jar
#CMD java -jar $APP_DOCKER_PATH

EXPOSE 8080