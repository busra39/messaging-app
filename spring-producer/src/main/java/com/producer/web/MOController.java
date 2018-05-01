package com.producer.web;

import com.producer.service.MOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.producer.entity.MessageOriented;

@RestController
public class MOController {

    @Autowired
    MOService ms;

    @PostMapping("/message")
    public String sendMessage(@RequestBody MessageOriented mo) {
        //MOEventProducer m = MOEventProducer.getInstance();
        if(ms.process(mo))
            return "Received";
        return "ERROR";
    }

}
