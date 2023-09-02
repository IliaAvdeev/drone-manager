FROM openjdk:19-alpine
LABEL authors="Ilia Avdeev"
MAINTAINER Ilia Avdeev
ADD drone-management-application/target/quarkus-app /app
RUN chmod -R o-rwx /app/app
CMD ["java", "-jar", "/app/quarkus-run.jar"]
EXPOSE 8080