FROM openjdk:8
ADD target/clientUI.jar clientUI.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "clientUI.jar"]