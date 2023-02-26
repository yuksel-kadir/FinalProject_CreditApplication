FROM openjdk:17-oracle
EXPOSE 8080
ADD target/*.jar creditapplication.jar
ENTRYPOINT ["java", "-jar", "/creditapplication.jar"]