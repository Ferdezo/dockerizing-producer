FROM maven:3.6-jdk-11 AS BUILD 

COPY /src /app/src

COPY pom.xml /app/pom.xml 

RUN mvn -f /app/pom.xml install


FROM openjdk:11.0.4-jre

RUN java -version

COPY --from=BUILD /app/target/discount-producer.jar /discount-producer.jar
COPY entrypoint.sh /
COPY waitEntrypoint.sh /
RUN chmod +x /waitEntrypoint.sh

EXPOSE 8080

ENTRYPOINT sh entrypoint.sh