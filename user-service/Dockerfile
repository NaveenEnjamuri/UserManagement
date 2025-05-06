FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/user-service-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# FROM openjdk:17-jdk-slim
# VOLUME /tmp
# COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

