package br.com.ronistone.feature.toggle.autofind.server;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {

    private MessageType type;
    private String name;
    private Object value;
    private UUID id;

    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, String name, Object value, UUID id) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
