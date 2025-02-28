FROM openjdk:17-jdk-alpine
LABEL author="CJMira"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]