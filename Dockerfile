# Usa una imagen base de OpenJDK
FROM openjdk:17-oracle
VOLUME /temp
ARG JAR_FILE

# Copia el archivo JAR de la aplicación a la imagen
COPY ${JAR_FILE} app.jar

# Define el comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "/app.jar"]

