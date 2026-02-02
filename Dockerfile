FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package && ls -la target

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/pokequiz.jar app.jar

RUN useradd -r -u 1001 appuser
USER appuser

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
