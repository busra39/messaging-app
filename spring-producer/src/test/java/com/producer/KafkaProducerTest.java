package com.producer;

/**
 * Created by busracanak on 01/05/18.
 */

import com.producer.entity.MessageOriented;
import com.producer.service.MOService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private MOService ms;

    private String s = "{\"id\":\"StringId\",\"type\":\"TESTABLE\",\"senderId\":\"SEND_SENDER\"}";

    @Before
    public void testMOEventProducer() {
        ms.process(new MessageOriented("StringId","TESTABLE","SEND_SENDER"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsume() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "MessageApp");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("messages"));

        System.out.println("STARTED");
        boolean b = true;
        try {
            while (b) {
                final ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    if(record.value().toString().equals(s)) {
                        b = false;
                        break;
                    }
                }
            }

        } finally {
            consumer.close();
        }
        System.out.println("END");
    }
}