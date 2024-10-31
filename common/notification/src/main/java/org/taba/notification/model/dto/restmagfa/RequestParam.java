package org.taba.notification.model.dto.restmagfa;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class RequestParam implements Serializable {

        public RequestParam() {
                senders = new ArrayList<>();
                messages = new ArrayList<>();
                recipients = new ArrayList<>();
        }

        public ArrayList<String> senders;
        public ArrayList<String> messages;
        public ArrayList<String> recipients;
}
