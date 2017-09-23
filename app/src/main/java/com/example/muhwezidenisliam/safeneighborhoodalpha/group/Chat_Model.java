package com.example.muhwezidenisliam.safeneighborhoodalpha.group;

import java.util.Date;

/**
 * Created by basasa on 9/13/2017.
 */

public class Chat_Model {
    private String messageText;

    public Chat_Model(String messageText, String messageUserPhone, String messageUserId) {
        this.messageText = messageText;
        this.messageUserPhone = messageUserPhone;
        this.messageUserId = messageUserId;
        this.messageTime = new Date().getTime();
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUserPhone() {
        return messageUserPhone;
    }

    public void setMessageUserPhone(String messageUserPhone) {
        this.messageUserPhone = messageUserPhone;
    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public Chat_Model() {
    }

    private String messageUserPhone;
    private String messageUserId;
    private long messageTime;
}
