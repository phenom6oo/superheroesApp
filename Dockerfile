FROM openjdk:17

# Copiamos el archivo JAR de la aplicación al contenedor
COPY target/SuperheroesApp-0.0.1-SNAPSHOT.jar.original /app/app.jar

# Establecemos el directorio de trabajo
WORKDIR /app

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]