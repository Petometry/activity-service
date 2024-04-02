FROM openjdk:21
EXPOSE 8080
ENV database_url = ""
ENV database_username = ""
ENV database_password = ""
ARG JAR_NAME
ADD /target/service.jar /service.jar
CMD java  -jar /service.jar