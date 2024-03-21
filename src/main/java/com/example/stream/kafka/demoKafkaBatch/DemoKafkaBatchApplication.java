package com.example.stream.kafka.demoKafkaBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class DemoKafkaBatchApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DemoKafkaBatchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaBatchApplication.class, args);
    }

    @Bean
    public ApplicationRunner runnerBatchSend(StreamBridge bridge) {
        return args -> {
            for (int i = 0; i < 100; i++) {
                bridge.send("producer-batch-out-0", "test-" + i);
            }
        };
    }

//    @Bean
//    public ApplicationRunner runnerSend(StreamBridge bridge) {
//        return args -> {
//            for (int i = 0; i < 500; i++) {
//                bridge.send("producer-single-out-0", "test-" + i);
//            }
//        };
//    }

    @Bean("consumerBatch")
    public Consumer<List<String>> consumerBatch() {
        return s -> s.forEach(s1 -> {
            LOG.info(s1);
            LOG.info("++++++++ CONSUMER Batch size: {}", s.size());
            LOG.info("++++++++ MDC Batch: {}", MDC.getCopyOfContextMap());
        });
    }

//    @Bean("consumerSingle")
//    public Consumer<String> consumerSingle() {
//        return s -> {
//            LOG.info("------- CONSUMER: {}", s);
//            LOG.info("------- MDC: {}", MDC.getCopyOfContextMap());
//        };
//    }
}
