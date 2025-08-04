# Etapa de Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e baixa dependências para otimizar cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila o projeto sem rodar testes
RUN mvn package -DskipTests

# Etapa de Produção (imagem menor)
FROM eclipse-temurin:21-alpine

# Define o diretório de trabalho
WORKDIR /app

# Cria um usuário não-root para segurança
RUN addgroup -g 1001 -S appgroup && \
    adduser -S appuser -u 1001 -G appgroup

# Copia apenas o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Muda a propriedade do arquivo para o usuário não-root
RUN chown appuser:appgroup app.jar

# Muda para o usuário não-root
USER appuser

# Expõe a porta do Spring Boot
EXPOSE 8080

# Define o comando de execução
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]