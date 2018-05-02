package com.consumer;

/**
 * Created by busracanak on 30/04/18.
 */


import com.consumer.entity.MessageOriented;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.apache.tomcat.jni.Time.now;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MOEntityTest {
    private MessageOriented mo;

    @Before
    public void setUp(){
        mo = new MessageOriented("id", "type", "senderId");
    }

    @Test
    public void shouldReturnMOId(){
        assertEquals("id", mo.getId());
    }

    @Test
    public void shouldReturnMOType(){
        assertEquals("type", mo.getType());
    }

    @Test
    public void shouldReturnMOSender(){
        MessageOriented test = mock(MessageOriented.class);
        when(test.getSenderId()).thenReturn("senderId");
        assertEquals(test.getSenderId(), "senderId");
    }

    @Test
    public void shouldReturnMOString(){
        assertEquals("MessageOriented{id=id, type=type, senderId=senderId}", mo.toString());
    }

    @Test
    public void shouldReturnMOTs() {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Timestamp t = new Timestamp(timeStampMillis);
        mo.setTs(t);
        assertEquals(t, mo.getTs());
    }
}