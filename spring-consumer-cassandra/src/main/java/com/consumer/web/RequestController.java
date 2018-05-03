package com.consumer.web;

import com.consumer.service.MOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by busracanak on 25/04/18.
 */

@Controller
public class RequestController {

    MOService moService;

    @Autowired
    public void setProductService(MOService moService) {
        this.moService = moService;
    }

    @CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
    @RequestMapping("/list")
    public @ResponseBody HashMap<String, Integer> list() {
        return moService.listAll();
    }

}