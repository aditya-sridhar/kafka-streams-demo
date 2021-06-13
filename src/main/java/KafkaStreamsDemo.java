import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.Properties;

public class KafkaStreamsDemo {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> views = builder.stream(
                "input-topic",
                Consumed.with(stringSerde, stringSerde)
        );

        KTable<String, Long> totalViews = views
                .mapValues(v -> Long.parseLong(v))
                .groupByKey(Grouped.with(stringSerde, longSerde))
                .reduce(Long::sum);

        totalViews.toStream().to("output-topic", Produced.with(stringSerde, longSerde));

        final Properties props = new Properties();
        props.putIfAbsent(StreamsConfig.APPLICATION_ID_CONFIG, "streams-totalviews");
        props.putIfAbsent(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
