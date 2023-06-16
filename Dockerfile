FROM openjdk:17 as build
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN /opt/maven/bin/mvn -B package

ARG JAR_FILE=target/recipes-api-0.0.1-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} .
EXPOSE 8080
ENTRYPOINT ["java","-jar","recipes-api-0.0.1-SNAPSHOT.jar"]