package com.producer.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.producer.entity.MessageOriented;

@Repository
public interface MOService {

    boolean process(MessageOriented mo);

}
