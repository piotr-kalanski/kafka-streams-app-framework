package com.datawizards.kafka.streams.app;

import com.datawizards.kafka.streams.app.config.ConfigurationLoader;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Base class for Kafka Streams applications
 */
public abstract class KafkaStreamsApplicationBase {
    protected Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Run Kafka Streams application
     */
    public void run() {
        log.info("Run Kafka Streams application");
        log.info("Loading configuration");
        Properties config = loadConfiguration();
        log.info(config.toString());
        log.info("Building topology");
        KafkaStreams streams = createTopology(config);
        log.info("Start streaming");
        startStream(streams);
    }

    private Properties loadConfiguration() {
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        return configurationLoader.loadConfiguration();
    }

    private KafkaStreams createTopology(Properties config) {
        KStreamBuilder builder = new KStreamBuilder();
        buildTopology(builder);
        return new KafkaStreams(builder, config);
    }

    /**
     * Build Kafka Streams topology
     */
    protected abstract void buildTopology(KStreamBuilder builder);

    private void startStream(KafkaStreams streams) {
        streams.cleanUp();
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
