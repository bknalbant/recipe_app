FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/recipe*.jar
COPY ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]