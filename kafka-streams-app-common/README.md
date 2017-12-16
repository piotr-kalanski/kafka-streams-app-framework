# Kafka Streams Applications framework

## Maven archetype

The easiest way to start with Kafka Streams Application framework is to create new project using maven archetype.
Instruction is provided here: [Maven archetype](/kafka-streams-app-archetype)

## Implement main application class

Main application class should extend ```KafkaStreamsApplicationBase``` and implement one method ```buildTopology``` responsible for creating Kafka Streams topology.

```java

import com.datawizards.kafka.streams.app.KafkaStreamsApplicationBase;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

public class ApplicationMain extends KafkaStreamsApplicationBase {

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.run();
    }

    protected void buildTopology(KStreamBuilder builder) {
        // TODO - build here Kafka Streams topology

        KStream<String, String> stream = builder.stream("TOPIC ???");
        //outputStream.to("output topic ???");
    }

}
```

## Application configuration

Configuration is automatically read by ```KafkaStreamsApplicationBase``` class from file ```resources/applications.conf```. Keys in configuration are the same as in Kafka Streams properties.

Example configuration:

```hocon
application.id = "example-user-profile"
bootstrap.servers = "localhost:9092"
schema.registry.url = "http://localhost:8081"
```

## Avro schemas

Avro schemas should be stored in ```resources\avro``` directory.

```avro-maven-plugin``` automatically is generating avro ```SpecificRecord``` in maven package phase and storing them in ```target``` directory.