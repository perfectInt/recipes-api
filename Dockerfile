FROM openjdk:17
ARG JAR_FILE=target/recipes-api-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/app
COPY ${JAR_FILE} recipes-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","recipes-api.jar"]