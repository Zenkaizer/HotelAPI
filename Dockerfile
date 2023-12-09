# Usa una imagen base de OpenJDK
#FROM openjdk:17-oracle

#ADD build/libs/*.jar codecrafters-0.0.1-SNAPSHOT.jar
#EXPOSE 9000
# Define el comando para ejecutar la aplicación Spring Boot
#CMD ["java", "-jar", "/codecrafters-0.0.1-SNAPSHOT.jar"]

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]