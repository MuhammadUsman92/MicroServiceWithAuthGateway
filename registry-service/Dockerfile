FROM eclipse-temurin:17-jdk-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} registry-service.jar
ENTRYPOINT ["java","-jar","/registry-service.jar"]
EXPOSE 8761