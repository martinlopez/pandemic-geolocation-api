package com.example.pandemic_monitoring.repository.streaming.impl;

import com.example.pandemic_monitoring.domain.locations.LocationEvent;
import com.example.pandemic_monitoring.repository.streaming.StreamingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

@Repository
public class LocationStreamingRepositoryImpl implements StreamingRepository {

    private final static String TOPIC = "locations02";

    private KafkaSender kafkaProducer;
    private KafkaReceiver kafkaReceiver;
    private ObjectMapper objectMapper = new ObjectMapper();


    LocationStreamingRepositoryImpl() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put("group.id", "test");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        //if scope is sender
        final SenderOptions<Integer, String> producerOptions = SenderOptions.create(props);
        kafkaProducer = KafkaSender.create(producerOptions);

        //if scope is receiver
        final ReceiverOptions receiverOptions = ReceiverOptions.create(props).subscription(Collections.singleton(TOPIC));
        kafkaReceiver = KafkaReceiver.create(receiverOptions);
    }

    /**
     * @param event
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public Mono<Void> publish(Object event) {
        System.out.println(event);
        String payload = toBinary(event);
        System.out.println(payload);
        SenderRecord<Integer, String, Integer> message = SenderRecord.create(new ProducerRecord<>(TOPIC, payload), 1);
        return kafkaProducer.send(Mono.just(message)).next();
    }

    @Override
    public void receive(Function<Object, Void> fn) {
        Flux<ReceiverRecord<Integer, String>> inboundFlux = kafkaReceiver.receive();
        inboundFlux.subscribe(r -> {
                    fn.apply(r);
                    //TODO: if fail send message to contingencies topic
                    r.receiverOffset().acknowledge();
                }
        );
    }


    /**
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    private String toBinary(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
