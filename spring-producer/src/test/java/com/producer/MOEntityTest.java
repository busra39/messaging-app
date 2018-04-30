package com.producer;

/**
 * Created by busracanak on 30/04/18.
 */


import com.producer.entity.MessageOriented;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

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

}