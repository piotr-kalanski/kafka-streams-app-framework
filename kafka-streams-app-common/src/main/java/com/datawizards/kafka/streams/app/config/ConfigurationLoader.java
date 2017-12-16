package com.datawizards.kafka.streams.app.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class ConfigurationLoader {

    public Properties loadConfiguration() {
        Config appConfig = ConfigFactory.load();
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, appConfig.getString(StreamsConfig.APPLICATION_ID_CONFIG));
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getString(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG));

        // Exactly once processing!!
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        config.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, appConfig.getString(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG));

        return config;
    }

}
