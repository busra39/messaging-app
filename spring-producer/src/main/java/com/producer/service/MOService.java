package com.producer.service;

import org.springframework.stereotype.Service;
import com.producer.entity.MessageOriented;

@Service
public interface MOService {

    boolean process(MessageOriented mo);

}
