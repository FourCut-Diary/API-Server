FROM amd64/amazoncorretto:17

WORKDIR /app

COPY ./module-api/build/libs/module-api-0.0.1-SNAPSHOT.jar /app/fourcutdiary.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active={profile}", "fourcutdiary.jar"]
