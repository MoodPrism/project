FROM anapsix/alpine-java:latest
COPY ./target/moodapp-0.0.1.jar ./root
WORKDIR ./root
ENTRYPOINT ["java", "-jar", "moodapp-0.0.1.jar"]
EXPOSE 8080
