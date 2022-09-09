FROM adoptopenjdk/openjdk11:alpine-jre

ADD /target/*.jar drone-service.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","drone-service.jar"]