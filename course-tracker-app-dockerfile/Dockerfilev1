FROM openjdk:17.0.2
ADD target/*.jar application.jar
ENTRYPOINT ["java", "-jar","application.jar"]
EXPOSE 8080