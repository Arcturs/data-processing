FROM maven:3.8.3-openjdk-17-slim as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /opt/build/event-generator
COPY pom.xml /opt/build/event-generator/
COPY src /opt/build/event-generator/src/
RUN mvn -f /opt/build/event-generator/pom.xml clean package -B -DskipTests


FROM openjdk:17-alpine
WORKDIR /opt/app/db
COPY --from=BUILDER /opt/build/event-generator/target/*.jar /opt/app/event-generator/event-generator.jar

RUN apk --no-cache add curl

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar", "/opt/app/event-generator/event-generator.jar"]