# Usa una imagen base de OpenJDK
FROM openjdk:17-oracle

# Copia el archivo JAR de la aplicación a la imagen
COPY build/libs/codecrafters-0.0.1-SNAPSHOT.jar /app.jar

# Define el comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "/app.jar"]