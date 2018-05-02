package com.consumer.service;

import com.consumer.entity.MessageOriented;
import com.datastax.driver.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by busracanak on 01/05/18.
 */

@Service
public class CassandraHandlerImpl implements CassandraHandler{

    @Autowired
    Session session;

    private static CassandraHandlerImpl singleton = new CassandraHandlerImpl( );

    public static CassandraHandlerImpl getInstance( ) {
        return singleton;
    }

    public void writeOne(MessageOriented mo) {
        BatchStatement batchStatement = new BatchStatement();
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Timestamp t = new Timestamp(timeStampMillis);
        mo.setTs(t);
        PreparedStatement preparedInsertExpense =
                session.prepare(
                        "INSERT INTO message (id,type,sender_id, insertion_time) "
                                + "VALUES (?,?,?,?)");
        ResultSetFuture rs = session.executeAsync(preparedInsertExpense.bind(mo.getId(), mo.getType(), mo.getSenderId()
        , t));

    }

    public HashMap<String, Integer>  handle(){
        return getList();
    }

    private HashMap<String, Integer> getList(){
        HashMap<String, Integer> response = new HashMap<>(5);
        response.put("MOCharged", getMOCharged());
        response.put("MOReceived", getMOReceived());
        response.put("MODelivered", getMODelivered());
        response.put("MOTerminated", getMOTerminated());
        response.put("MOReport", getMOReport());
        return response;
    }

    private int getMOReceived(){
        final ResultSet rows = session.execute("Select * from message where type='MOReceived' allow filtering" );
        Optional<Integer> size = Optional.ofNullable(rows.all().size());
        if(size.isPresent())
            return size.get();
        return 0;
    }

    private int getMODelivered(){
        final ResultSet rows = session.execute("Select * from message where type='MODelivered' allow filtering" );
        Optional<Integer> size = Optional.ofNullable(rows.all().size());
        if(size.isPresent())
            return size.get();
        return 0;
    }

    private int getMOTerminated(){
        final ResultSet rows = session.execute("Select * from message where type='MOTerminated' allow filtering" );
        Optional<Integer> size = Optional.ofNullable(rows.all().size());
        if(size.isPresent())
            return size.get();
        return 0;
    }

    private int getMOReport(){
        final ResultSet rows = session.execute("Select * from message where type='MOReport' allow filtering" );
        Optional<Integer> size = Optional.ofNullable(rows.all().size());
        if(size.isPresent())
            return size.get();
        return 0;
    }

    private int getMOCharged(){
        final ResultSet rows = session.execute("Select * from message where type='MOCharged' allow filtering" );
        Optional<Integer> size = Optional.ofNullable(rows.all().size());
        if(size.isPresent())
            return size.get();
        return 0;
    }
}
