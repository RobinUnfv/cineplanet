# ==================== ETAPA 1: BUILD ====================
FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /app

RUN apk add --no-cache maven

COPY . .

RUN mvn clean install -DskipTests -pl discovery-server -am

FROM eclipse-temurin:25-jre-alpine

RUN apk add --no-cache curl bash

WORKDIR /app

COPY --from=build /app/discovery-server/target/discovery-server-*.jar app.jar

EXPOSE 8761

ENV DISCOVERY_SERVICE_PROFILE=server01

ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=${DISCOVERY_SERVICE_PROFILE}"]