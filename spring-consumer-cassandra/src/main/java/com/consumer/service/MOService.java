package com.consumer.service;

import com.consumer.entity.MessageOriented;

import java.util.HashMap;

public interface MOService {

    HashMap<String, Integer> listAll();

    MessageOriented save(MessageOriented product);

}
