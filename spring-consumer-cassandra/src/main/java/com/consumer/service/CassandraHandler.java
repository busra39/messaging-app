package com.consumer.service;

import com.consumer.entity.MessageOriented;
import com.datastax.driver.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by busracanak on 01/05/18.
 */

@Service
public interface CassandraHandler {

    void writeOne(MessageOriented mo);

    HashMap<String, Integer>  handle();

}
