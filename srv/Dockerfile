FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=cloud","/app.war"]