package com.example.stream.kafka.demoKafkaBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.BatchListenerFailedException;

import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class DemoKafkaBatchApplication {

    private Logger LOG = LoggerFactory.getLogger(DemoKafkaBatchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaBatchApplication.class, args);
    }

    @Bean
    public ApplicationRunner runnerBatchSend(StreamBridge bridge) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                bridge.send("producerBatch-out-0", "test-" + i);
            }
        };
    }

    @Bean
    public Consumer<List<String>> consumerBatch() {
        return s -> {
            s.stream().forEach(s1 -> {
                LOG.info(s1);
                if (s1.equals("test-5")) {
                    throw new BatchListenerFailedException(s1, 5);
                }
            });
        };
    }

}
