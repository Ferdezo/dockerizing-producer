FROM adoptopenjdk:11-jre-hotspot AS BUILD 
WORKDIR /app
COPY target/discount-producer.jar ./
RUN java -Djarmode=layertools -jar discount-producer.jar extract
RUN ls /app

FROM openjdk:11.0.4-jre
RUN java -version
COPY --from=BUILD app/dependencies/ ./
COPY --from=BUILD app/snapshot-dependencies/ ./
COPY --from=BUILD app/resources/ ./
COPY --from=BUILD app/application/ ./
COPY entrypoint.sh ./
COPY waitEntrypoint.sh ./
RUN chmod +x /waitEntrypoint.sh

EXPOSE 8080

ENTRYPOINT sh entrypoint.sh