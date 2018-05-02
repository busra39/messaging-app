package com.consumer.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.consumer.entity.MessageOriented;

@Service
public class MOConsumer {
    private static final Logger log = LoggerFactory.getLogger(MOConsumer.class);

    @Autowired
    CassandraHandler cassandra;

    @KafkaListener(topics = "messages")
    public void onReceiving(MessageOriented mo, @Header(KafkaHeaders.OFFSET) Integer offset,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("topic = {}, partition = {}, offset = {}, MO = {}",
                topic, partition, offset, mo);

        cassandra.writeOne(mo);
    }
}
