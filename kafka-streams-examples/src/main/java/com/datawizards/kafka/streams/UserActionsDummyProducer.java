package com.datawizards.kafka.streams;

import com.datawizards.model.UserAction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class UserActionsDummyProducer {
    private static final String INPUT_TOPIC = "user-actions-example";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties config = new Properties();
        config.put("client.id", "write-avro-message");
        config.put("bootstrap.servers", "localhost:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        config.put("schema.registry.url", "http://localhost:8081");
        config.put("acks", "all");

        KafkaProducer kafkaProducer = new KafkaProducer<String, UserAction>(config);
        Random r = new Random();

        String[] eventTypes = new String[] {"LISTING_VIEW", "APPLICATION", "LOGIN", "REGISTER"};
        String[] devices = new String[] {"DESKTOP", "MOBILE_APP", "MOBILE_WWW", "TABLE_WWW"};

        for(int i=0;i<1000;++i) {
            String userId = "user_" + r.nextInt(10);

            kafkaProducer.send(new ProducerRecord<>(
                    INPUT_TOPIC,
                    userId,
                    UserAction.newBuilder()
                            .setEventId("event" + i)
                            .setEventType(eventTypes[r.nextInt(eventTypes.length-1)])
                            .setSessionId("session" + r.nextInt(100))
                            .setEventDate(DateTime.now())
                            .setListingId("" + r.nextInt(100))
                            .setUserId(userId)
                            .setDevice(devices[r.nextInt(devices.length-1)])
                            .build()
            )).get();
        }
    }
}
