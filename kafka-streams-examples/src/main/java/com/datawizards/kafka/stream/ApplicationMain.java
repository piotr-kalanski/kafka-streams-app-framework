package com.datawizards.kafka.stream;

import com.datawizards.kafka.streams.app.KafkaStreamsApplicationBase;
import com.datawizards.model.UserAction;
import com.datawizards.model.UserProfile;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class ApplicationMain extends KafkaStreamsApplicationBase {
    private static final String INPUT_TOPIC = "user-actions-example";
    private static final String OUTPUT_TOPIC = "user-profile-example";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ApplicationMain app = new ApplicationMain();
        app.run();
    }

    protected void buildTopology(KStreamBuilder builder) {
        SpecificAvroSerde<UserProfile> userProfileSpecificAvroSerde = new SpecificAvroSerde<>();
        userProfileSpecificAvroSerde.configure(Collections.singletonMap(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081"), false);

        KStream<String, UserAction> userActions = builder.stream(INPUT_TOPIC);

        KTable<String, UserProfile> userProfile = userActions
            .groupByKey()
            .aggregate(
                this::emptyProfile,
                this::aggregateProfile,
                userProfileSpecificAvroSerde
            );

        userProfile.to(OUTPUT_TOPIC);
    }

    private UserProfile emptyProfile() {
        return UserProfile
                .newBuilder()
                .setUserId("???")
                .build();
    }

    private UserProfile aggregateProfile(String userId, UserAction action, UserProfile userProfile) {
        return UserProfile
                .newBuilder()
                .setUserId(userId)
                .setActionsCount(userProfile.getActionsCount() + 1)
                .setLastAction(action.getEventDate())
                .build();
    }

}
