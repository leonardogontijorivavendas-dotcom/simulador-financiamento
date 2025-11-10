# Etapa 1: build com Maven e Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -e

# Verificação (debug) – mostra o conteúdo gerado
RUN ls -la target

# Etapa 2: imagem final (runtime)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia qualquer .jar gerado (não depende do nome)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
