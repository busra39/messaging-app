package com.consumer;

import com.consumer.config.MOType;
import com.consumer.entity.MessageOriented;
import com.consumer.repository.MORepository;
import com.consumer.service.MOServiceImpl;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by busracanak on 03/05/18.
 */

public class MORepositoryTest {

    private MORepository moRepository = mock(MORepository.class);
    private MOServiceImpl moService;

    @Before
    public void setUp() {
        when(moRepository.findRecord(MOType.MOREPORT)).thenReturn(2);
        when(moRepository.findRecord(MOType.MODELIVERED)).thenReturn(3);
        when(moRepository.findRecord(MOType.MOTERMINATED)).thenReturn(8);

        MessageOriented mo = new MessageOriented("id1", "type1", "sender1");
        when(moRepository.save(mo)).thenReturn(mo);
        moService = new MOServiceImpl(moRepository);
    }

    @Test
    public void whengetListAll_ReturnMap() {
        assertNotNull(moService.listAll());
        HashMap<String, Integer> map = moService.listAll();
        assertEquals(map.size(), 5);
        assertEquals(map.get(MOType.MOREPORT).intValue(), 2);
        assertEquals(map.get(MOType.MODELIVERED).intValue(), 3);
        assertEquals(map.get(MOType.MOTERMINATED).intValue(), 8);
    }

    @Test
    public void whenSave_ReturnObject() {
        MessageOriented mo = new MessageOriented("id1", "type1", "sender1");
        MessageOriented moRet = moService.save(mo);
        assertEquals(moRet.getId(), mo.getId());
        assertEquals(moRet.getType(), mo.getType());
    }


}
