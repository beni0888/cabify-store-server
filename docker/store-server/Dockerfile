FROM openjdk:8-jdk-alpine
LABEL maintainer="beni0888@hotmail.com" maintainer="jesus.benito.c@gmail.com"
ADD cabify-store-server-bootapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]