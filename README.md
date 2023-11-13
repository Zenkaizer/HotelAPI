# HOTEL API - Proyecto Integrador: Software

API diseñada para responder a los requerimientos establecidos por el equipo CodeCrafters para el proyecto integrador: software.

## Instalación de la API

Tener Java instalado

Crear una base de datos en MySQL

Clonar el Repositorio:

```bash
git clone https://Marxx0103@bitbucket.org/codecraftersucn/hotelapi.git
```
En el archivo "application.yaml", completar los campos faltantes donde están los "[ ]". En el campo "MYSQL PORT" por defecto es el puesto 3306. En el campo [DATABASE NAME], reemplazar por el nombre de la base de datos MySQL creada anteriormente. En el campo [PASSWORD] indicar la contraseña de la base de datos.

En caso de no funcionar, cambiar el campo "spring.datasource.username" por el username correcto.

```properties
spring:
jpa:
database: MYSQL
show-sql: true
hibernate:
ddl-auto: update

datasource:
url: jdbc:mysql://localhost:[MYSQL PORT]/[DATABASE NAME]
username: root
password: [PASSWORD]
sql:
init:
platform: org.hibernate.dialect.MySQL8Dialect

server:
port: 9000

```

Para iniciar el proyecto, es necesario utilizar una IDE como IntelliJ para correr de manera correcta el proyecto de Spring con Maven. Para correr el proyecto es necesario ejecutar el método main de la clase "Practica3Application", no sin antes esperar que las dependencias del proyecto estén completamente instaladas.

## Realizado por
Equipo CodeCrafters - Universidad Católica del Norte - 2023
