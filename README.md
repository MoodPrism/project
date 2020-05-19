# Follow the following steps to run the application:

#### RUN KAFKA
```
docker-compose up -d
```

#### BUILD AND START MOODPRISM (inside moodprism folder)
```
docker build -t moodprism .
```
```
docker run --rm -it --network project_isolated_nw -p 8080:8080 --name moodprism moodprism
```

#### START CLIENTAPP (inside clientApp folder)
Windows:
```
java -jar target\clientapp-0.0.1-SNAPSHOT.jar
```
Unix:
```
java -jar target/clientapp-0.0.1-SNAPSHOT.jar
```

#### START MOODAPP (inside moodApp folder)
Windows:
```
java -jar target\moodapp-0.0.1-SNAPSHOT.jar
```
Unix:
```
java -jar target/moodApp-0.0.1-SNAPSHOT.jar
```
#### START TESTINGCONSUMER (inside testingConsumer folder)
Windows:
```
java -jar target\TestingConsumer-0.0.1-SNAPSHOT.jar
```
Unix:
```
java -jar target/TestingConsumer-0.0.1-SNAPSHOT.jar
```
#### TEST THE APPLICATION

http://localhost:8080/
