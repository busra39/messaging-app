package com.consumer;

import com.consumer.entity.MessageOriented;
import com.consumer.service.MOConsumer;
import com.consumer.service.MOService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * Created by busracanak on 03/05/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaConsumerTest {
    @Autowired
    MOConsumer moConsumer;

    @Test
    @Ignore
    public void produce(){

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9091");
        props.put("group.id", "MessageApp");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord<String, String>("messages", "1", "1"));
        producer.close();
    }

}
