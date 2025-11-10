# ============================
# 🏗️ Etapa 1 - Build do projeto
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o código-fonte do projeto
COPY . .

# Compila o projeto sem rodar os testes
RUN mvn clean package -DskipTests


# ==============================
# 🚀 Etapa 2 - Executar o aplicativo
# ==============================
FROM eclipse-temurin:21
WORKDIR /app

# Copia o arquivo JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
