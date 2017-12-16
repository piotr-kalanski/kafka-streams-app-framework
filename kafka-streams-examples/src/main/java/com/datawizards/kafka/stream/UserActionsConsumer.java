package com.datawizards.kafka.stream;

import com.datawizards.model.UserAction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class UserActionsConsumer {
    private static final String TOPIC = "user-actions-example";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties config = new Properties();
        config.put("group.id", "user-actions-consumer");
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        config.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        config.put("schema.registry.url", "http://localhost:8081");

        KafkaConsumer<String, UserAction> consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Collections.singletonList(TOPIC));
        try {
            while (true) {
                ConsumerRecords<String, UserAction> records = consumer.poll(100);
                for (ConsumerRecord<String, UserAction> record : records)
                {
                    System.out.println(record.key() + "->" + record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
