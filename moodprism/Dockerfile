FROM anapsix/alpine-java:latest
COPY ./target/moodprism-0.0.1.jar ./root
WORKDIR ./root
ENTRYPOINT ["java", "-jar", "moodprism-0.0.1.jar"]
EXPOSE 8080
