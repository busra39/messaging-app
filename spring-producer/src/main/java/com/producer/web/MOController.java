package com.producer.web;

import com.producer.service.MOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.producer.entity.MessageOriented;

@RestController
public class MOController {

    @Autowired
    private MOService MOService;

    @PostMapping("/message")
    public String sendMessage(@RequestBody MessageOriented mo) {
        if(this.MOService.process(mo))
            return "Received";
        return "ERROR";
    }
}
