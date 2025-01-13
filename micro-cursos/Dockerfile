FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app

# Instalar dependencias necesarias
RUN apk add --no-cache maven

# Configurar la codificación
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

# Copiar archivos del proyecto
COPY . .

# Establecer permisos y ejecutar Maven
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests -Dfile.encoding=UTF-8

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "app.jar"]