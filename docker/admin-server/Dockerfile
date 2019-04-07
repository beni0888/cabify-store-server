FROM openjdk:8-jdk-alpine
LABEL maintainer="beni0888@hotmail.com" maintainer="jesus.benito.c@gmail.com"
ADD spring-boot-admin-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 10011
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]