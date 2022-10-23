FROM maven:3.8.5-openjdk-17 AS BUILD

COPY /src /app/src

COPY pom.xml /app/pom.xml

RUN mvn -f /app/pom.xml install


FROM openjdk:17.0.1

RUN java -version

COPY --from=BUILD /app/target/discount-producer.jar /discount-producer.jar
COPY entrypoint.sh /
COPY waitEntrypoint.sh /
RUN chmod +x /waitEntrypoint.sh

EXPOSE 8080

ENTRYPOINT sh entrypoint.sh