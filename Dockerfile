# Utiliza la imagen oficial de OpenJDK con la versión que necesitas
FROM openjdk:17
FROM gradle:8.5

# Establece el directorio de trabajo en la raíz del contenedor
WORKDIR /app

# Copia el archivo build.gradle y settings.gradle para aprovechar el caché de dependencias
COPY build.gradle .
COPY settings.gradle .

# Copia todos los archivos fuente al directorio de trabajo
COPY src ./src

# Construye el proyecto con Gradle
RUN gradle build

# Expone el puerto en el que se ejecutará tu aplicación Spring Boot (ajústalo según tus necesidades)
EXPOSE 9000

# Comando para ejecutar la aplicación Spring Boot al iniciar el contenedor
CMD ["java", "-jar", "build/libs/codecrafters-0.0.1-SNAPSHOT.jar"]