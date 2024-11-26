FROM amazoncorretto:21

RUN yum update -y && yum install -y glibc

ARG JAR_FILE=build/libs/ChalletService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
