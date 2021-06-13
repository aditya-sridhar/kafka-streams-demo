# Kafka Streams Demo

This repository shows how a simple kafka streams job can be written

# Pre-requisites

Please ensure Maven and Java are installed in your system
Also ensure you have kafka installed in your system and that kafka is up and running.

# Compiling the code

In order the compile the code and create an jar file, run the following command:
```bash
mvn clean package
```

This will create a file called **kafka-streams-demo-1.0-SNAPSHOT-jar-with-dependencies.jar** in the **target** folder

# Running the kafka streams job

You can run the jar file using the following command

```bash
java -jar target/kafka-streams-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
```