package com.consumer;

import com.consumer.config.CassandraConf;
import com.consumer.entity.MessageOriented;
import com.consumer.repository.MORepository;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CassandraProperties.class, CassandraConf.class})
@EnableCassandraRepositories("com.consumer.repository")
public class CassandraTest {

    /* Will use defaultKeySpace and message table  by deafult*/

    @Autowired
    MORepository moRepository;

    @Test
    public void savesObjToTable() {
        final MessageOriented mo1 = new MessageOriented("id1", "name1", "sender1");
        final MessageOriented mo2 = new MessageOriented("id2", "name2", "sender2");
        final MessageOriented mo3 = new MessageOriented("id3", "name3", "sender3");
        moRepository.save(mo1);
        moRepository.save(mo2);
        moRepository.save(mo3);

        final List<MessageOriented> mos = moRepository.findAll();
        assertEquals(mos.size(), 3);
    }

    @Test
    public void findByType() {
        final MessageOriented mo1 = new MessageOriented("id1", "type1", "sender1");
        final MessageOriented mo2 = new MessageOriented("id2", "type2", "sender2");
        final MessageOriented mo3 = new MessageOriented("id3", "type3", "sender3");
        moRepository.save(mo1);
        moRepository.save(mo2);
        moRepository.save(mo3);

        int count = moRepository.findRecord("type1");
        assertEquals(count, 1);

        count = moRepository.findRecord("type2");
        assertEquals(count, 1);

        count = moRepository.findRecord("type3");
        assertEquals(count, 1);

        count = moRepository.findRecord("sender4");
        assertEquals(count, 0);
    }
}