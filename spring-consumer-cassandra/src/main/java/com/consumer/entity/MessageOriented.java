package com.consumer.entity;

import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Table("message")
public class MessageOriented implements Serializable{


    @PrimaryKey
    @CassandraType(type = DataType.Name.TEXT)
    private String id;
    private String type;
    private String senderId;
    @CassandraType(type = DataType.Name.TIMESTAMP)
    private Timestamp ts;

    @JsonCreator
    public MessageOriented(@JsonProperty("id") String id, @JsonProperty("type") String type,
                           @JsonProperty("senderId") String senderId) {
        this.id = id;
        this.type = type;
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Timestamp getTs() {

        return ts;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("senderId", senderId)
                .toString();
    }

}
