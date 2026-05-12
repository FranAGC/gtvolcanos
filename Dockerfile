# ============================================================
# STAGE 1: Build — JDK completo para compilar
# ============================================================
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Copiamos Gradle primero para cachear dependencias.
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true

# Código fuente aparte para no invalidar el cache de deps
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test


# ============================================================
# STAGE 2: Runtime — solo JRE, imagen más liviana
# ============================================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Usuario sin privilegios (nunca correr como root en producción)
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Directorio de logs con permisos correctos
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app/logs

# Solo el JAR llega a la imagen final, sin JDK ni código fuente
COPY --from=builder /app/build/libs/*.jar app.jar

USER appuser
EXPOSE 8080

# Espera 60s al arranque de Spring antes del primer check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# UseContainerSupport: respeta límites de RAM del contenedor
# MaxRAMPercentage: usa máximo el 75% de la RAM asignada
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseG1GC", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]