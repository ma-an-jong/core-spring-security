FROM openjdk:17-jdk-slim-buster
COPY ./build/libs/core-spring-security-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources resources
EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar"]