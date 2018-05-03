package com.consumer.service;

import com.consumer.config.MOType;
import com.consumer.entity.MessageOriented;
import com.consumer.repository.MORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

@Service
public class MOServiceImpl implements MOService {

    private MORepository moRepository;

    @Autowired
    public MOServiceImpl(MORepository moRepository) {
        this.moRepository = moRepository;
    }

    @Override
    public HashMap<String, Integer> listAll() {
        //List<MessageOriented> moList = new ArrayList<>();
        //moRepository.findAll().forEach(moList::add);

        HashMap<String, Integer> map = new HashMap<>();
        map.put(MOType.MORECEIVED, getByType(MOType.MORECEIVED));
        map.put(MOType.MOCHARGED, getByType(MOType.MOCHARGED));
        map.put(MOType.MOTERMINATED, getByType(MOType.MOTERMINATED));
        map.put(MOType.MODELIVERED, getByType(MOType.MODELIVERED));
        map.put(MOType.MOREPORT, getByType(MOType.MOREPORT));

        return map;
    }

    private int getByType(String str){
        return moRepository.findRecord(str);
    }

    @Override
    public MessageOriented save(MessageOriented mo) {
        Instant now = Instant.now();
        mo.setTs(Timestamp.from(now));
        moRepository.save(mo);
        return mo;
    }

}
