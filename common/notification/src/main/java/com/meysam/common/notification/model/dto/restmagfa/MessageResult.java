package com.meysam.common.notification.model.dto.restmagfa;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class MessageResult implements Serializable {
    public int status;
    public ArrayList<Message> messages;
}
