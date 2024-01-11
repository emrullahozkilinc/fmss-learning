FROM maven:3.9.6-amazoncorretto-21 AS build
COPY ./src /usr/src/app/src
COPY ./pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -D skipTests

FROM amazoncorretto:21-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/local/lib/spring-dist-lock.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/spring-dist-lock.jar"]