FROM maven:3-amazoncorretto-17

ENV PORT=${PORT} \
    PROFILE=${PROFILE}

COPY . .
RUN mvn -B package --file pom.xml -DskipTests
RUN cp target/*.jar app.jar
EXPOSE ${PORT}

CMD ["java", "-Xmx512m", "-jar", "-Dserver.port=${PORT}", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]
