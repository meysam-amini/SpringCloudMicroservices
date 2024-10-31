package org.taba.notification.model.dto.restmagfa;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    public int status;
    public long id;
    public long userId;
    public long parts;
    public double tariff;
    public String alphabet;
    public String recipient;
}
