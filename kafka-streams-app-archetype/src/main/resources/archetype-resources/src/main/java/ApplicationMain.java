package ${groupId};

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
        // TODO - build Kafka Streams topology

        KStream<String, String> stream = builder.stream("TOPIC ???");
        //outputStream.to("output topic ???");
    }

}