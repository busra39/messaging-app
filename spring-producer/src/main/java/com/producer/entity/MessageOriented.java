package com.producer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class MessageOriented {

    private String id;
    private String type;
    private String senderId;

    public MessageOriented() {
    }

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("senderId", senderId)
                .toString();
    }
}
