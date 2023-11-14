package com.example.stream.kafka.demoKafkaBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
public class DemoKafkaApplication {

    private Logger LOG = LoggerFactory.getLogger(DemoKafkaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaApplication.class, args);
    }

    @Bean
    public ApplicationRunner runnerBatchSend(StreamBridge bridge) {
        return args -> {
            final Map<String, String> headers = new HashMap<>();
            headers.put("partitionKey", "foo");
            for (int i = 0; i < 10000; i++) {
                bridge.send("producer-partition-expression-out-0", MessageBuilder.withPayload("test-" + i).copyHeaders(headers).build());
            }
        };
    }

    @Bean
    public Consumer<Message<String>> consumer() {
        return s -> {
            LOG.info("message partition ID: {}", s.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION));
            LOG.info("message value: {}", s.getPayload());
        };
    }

}
