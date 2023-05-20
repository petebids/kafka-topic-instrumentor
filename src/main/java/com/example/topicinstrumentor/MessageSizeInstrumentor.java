package com.example.topicinstrumentor;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageSizeInstrumentor {

    private final MeterRegistry meterRegistry;

    @KafkaListener(topics = "test", groupId = "instrumentor")
    public void handle(ConsumerRecord<String, byte[]> record) {

        final String topic = record.topic();
        final String key = record.key();
        final int length = record.value().length;

        meterRegistry.summary(topic + "message-size", Tags.of(key, String.format("%d", length)));

    }
}
