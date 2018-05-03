package com.consumer.repository;

import com.consumer.entity.MessageOriented;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MORepository extends CrudRepository<MessageOriented, UUID> {

    @Query("select count(*) from message where type =?0 allow filtering")
    int findRecord(String type);

    @Query("select id, type, senderid from message")
    List<MessageOriented> findAll();

}
