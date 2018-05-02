package com.consumer.web;

import com.consumer.service.CassandraHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

/**
 * Created by busracanak on 25/04/18.
 */

@RestController
public class RequestController {

    @Autowired
    CassandraHandler cassandra;

    @RequestMapping("/list")
    public HashMap<String, Integer>list() {
        return cassandra.handle();
    }

}