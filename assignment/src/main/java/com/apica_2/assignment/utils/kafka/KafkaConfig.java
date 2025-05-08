package com.apica_2.assignment.utils.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String consumerBootstrapServers;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String producerBootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;


    @Bean
    @ConditionalOnProperty(name = "spring.kafka.producer.bootstrap-servers")
    public ProducerFactory<String, String> producerFactory() {
        try {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 300000000);
            configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 20000000);
            configProps.put(ProducerConfig.LINGER_MS_CONFIG, 100);
            configProps.put(ProducerConfig.RETRIES_CONFIG, 10);
            configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
            return new DefaultKafkaProducerFactory<>(configProps);
        }catch (Exception e) {
            logger.warn("Kafka Producer is not available: " + e.getMessage());
            return null;
        }
    }


    @Bean
    @ConditionalOnProperty(name = "spring.kafka.producer.bootstrap-servers")
    public KafkaTemplate<String, String> kafkaTemplate() {
        ProducerFactory<String, String> producerFactory = producerFactory();
        if (producerFactory != null) {
            return new KafkaTemplate<>(producerFactory);
        } else {
            return null;
        }
    }

    @Bean
    @ConditionalOnProperty(name = "spring.kafka.consumer.bootstrap-servers")
    public ConsumerFactory<String, String> consumerFactory() {
        try {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 150);
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 600000);
            props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 52428800);
            props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
            props.put(ProducerConfig.RETRIES_CONFIG, 3);
            props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 5000);
            props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);  // Block up to 60 seconds before throwing an error
            return new DefaultKafkaConsumerFactory<>(props);
        }catch (Exception e) {
            logger.warn("Kafka Consumer is not available: " + e.getMessage());
            return null;
        }
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        try {
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            factory.setBatchListener(true);
            factory.setConcurrency(1);
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);  // Set manual acknowledgment
            return factory;
        } catch (Exception e) {
            logger.warn("Kafka Listener Container Factory is not available: " + e.getMessage());
            return null;
        }
    }
}
