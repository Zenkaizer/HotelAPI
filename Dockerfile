# Usa una imagen base de OpenJDK
FROM openjdk:17-oracle

ADD build/libs/*.jar codecrafters-0.0.1-SNAPSHOT.jar
EXPOSE 9000
# Define el comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "/codecrafters-0.0.1-SNAPSHOT.jar"]
