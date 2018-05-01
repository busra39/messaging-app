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
        MessageOriented test = mock(MessageOriented.class);
        when(test.getId()).thenReturn("iid");
        assertEquals(test.getId(), "iid");
    }

    @Test
    public void shouldReturnMOType(){
        MessageOriented test = mock(MessageOriented.class);
        when(test.getType()).thenReturn("typee");
        assertEquals(test.getType(), "typee");
    }

    @Test
    public void shouldReturnMOSender(){
        MessageOriented test = mock(MessageOriented.class);
        when(test.getSenderId()).thenReturn("sender");
        assertEquals(test.getSenderId(), "sender");
    }

    @Test
    public void shouldSetSender(){
        mo.setSenderId("SEND");
        assertEquals(mo.getSenderId(), "SEND");
    }

    @Test
    public void shouldSetId(){
        mo.setId("ID");
        assertEquals(mo.getId(), "ID");
    }

    @Test
    public void shouldSetType(){
        mo.setType("TY");
        assertEquals(mo.getType(), "TY");
    }

    @Test
    public void shouldReturnMOString(){
        assertEquals("MessageOriented{id=id, type=type, senderId=senderId}", mo.toString());
    }

}