package com.consumer.web;

import com.consumer.entity.MessageOriented;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by busracanak on 25/04/18.
 */
@RestController
public class RequestController {

    @Autowired
    Session session;

    @RequestMapping("/list")
    public String list() {
        final ResultSet rows = session.execute("SELECT * FROM votes");

        ArrayList<MessageOriented> results = new ArrayList<>();

        for (Row row : rows.all()) {
            results.add(new MessageOriented(row.getString(0), row.getString(1), row.getString(2)));
        }
        return "test";
    }

}