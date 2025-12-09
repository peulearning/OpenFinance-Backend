FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /usr/src/app
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src
RUN ./mvnw -DskipTests package -Pprod


FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /usr/src/app/target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]