FROM openjdk:11.0.3-jdk

# The application's jar file - will be overrided with maven-docker-plugin through param buildArgs->JAR_FILE
ARG JAR_FILE=target/school-class-service-1.0.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} school-class-service.jar

EXPOSE 10000

CMD java -jar -Dspring.profiles.active=container /school-class-service.jar