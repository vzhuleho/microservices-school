FROM openjdk:11-jre-slim
COPY build/libs/scheduleservice-0.0.1-SNAPSHOT.jar /opt
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/opt/scheduleservice-0.0.1-SNAPSHOT.jar"]