FROM openjdk:19-jdk-alpine
ARG JAR_FILE=100ideas/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]