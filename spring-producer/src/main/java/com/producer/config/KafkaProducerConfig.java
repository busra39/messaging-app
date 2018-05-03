package com.producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.producer.entity.MessageOriented;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public ProducerFactory<String, MessageOriented> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), stringKeySerializer(), messageJsonSerializer());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrap());
        props.put("value.serializer", "com.consumer.entity.Mseri");
        return props;
    }

    @Bean
    public KafkaTemplate<String, MessageOriented> messageKafkaTemplate() {
        KafkaTemplate<String, MessageOriented> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setDefaultTopic(kafkaProducerProperties.getTopic());
        return kafkaTemplate;
    }

    @Bean
    public Serializer stringKeySerializer() {
        return new StringSerializer();
    }

    @Bean
    public Serializer messageJsonSerializer() {
        return new JsonSerializer<MessageOriented>();
    }

    /* Did not work with version 2.0.5*/
    @Bean
    public ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer(
            KafkaProperties kafkaProperties,
            ObjectProvider<RecordMessageConverter> messageConverterObjectProvider,
            ObjectProvider<KafkaTemplate<Object, Object>> kafkaTemplateObjectProvider) {

        RecordMessageConverter messageConverter = messageConverterObjectProvider.getIfUnique();
        KafkaTemplate<Object, Object> kafkaTemplate = kafkaTemplateObjectProvider.getIfUnique();

        return new ConcurrentKafkaListenerContainerFactoryConfigurer() {

            @Override
            public void configure(ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory,
                                  ConsumerFactory<Object, Object> consumerFactory) {

                listenerFactory.setConsumerFactory(consumerFactory);
                configureListenerFactory(listenerFactory);
                configureContainer(listenerFactory.getContainerProperties());
            }

            private void configureListenerFactory(
                    ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {
                PropertyMapper map = PropertyMapper.get();
                KafkaProperties.Listener properties = kafkaProperties.getListener();
                map.from(properties::getConcurrency).whenNonNull().to(factory::setConcurrency);
                map.from(() -> messageConverter).whenNonNull()
                        .to(factory::setMessageConverter);
                map.from(() -> kafkaTemplate).whenNonNull().to(factory::setReplyTemplate);
                map.from(properties::getType).whenEqualTo(KafkaProperties.Listener.Type.BATCH)
                        .toCall(() -> factory.setBatchListener(true));
            }

            private void configureContainer(ContainerProperties container) {
                PropertyMapper map = PropertyMapper.get();
                KafkaProperties.Listener properties = kafkaProperties.getListener();
                map.from(properties::getAckMode).whenNonNull().to(container::setAckMode);
                map.from(properties::getAckCount).whenNonNull().to(container::setAckCount);
                map.from(properties::getAckTime).whenNonNull().as(Duration::toMillis)
                        .to(container::setAckTime);
                map.from(properties::getPollTimeout).whenNonNull().as(Duration::toMillis)
                        .to(container::setPollTimeout);
                map.from(properties::getNoPollThreshold).whenNonNull()
                        .to(container::setNoPollThreshold);
                map.from(properties::getIdleEventInterval).whenNonNull().as(Duration::toMillis)
                        .to(container::setIdleEventInterval);
                map.from(properties::getMonitorInterval).whenNonNull().as(Duration::getSeconds)
                        .as(Number::intValue).to(container::setMonitorInterval);
            }

        };
    }

}
