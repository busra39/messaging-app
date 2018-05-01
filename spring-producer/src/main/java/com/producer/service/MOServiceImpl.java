package com.producer.service;

import com.producer.entity.MessageOriented;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class MOServiceImpl implements MOService{

    @Autowired
    private KafkaTemplate<String, MessageOriented> messagesKafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MOServiceImpl.class);

    public boolean process(MessageOriented mo) {
        try {
            SendResult<String, MessageOriented> sendResult = messagesKafkaTemplate.sendDefault(mo.getId(), mo).get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            LOGGER.info("topic = {}, partition = {}, offset = {}, MO = {}",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), mo);
            return true;
        } catch (Exception e) {
            LOGGER.info("Exception : ", e.toString());
            return false;
        }
    }
}
