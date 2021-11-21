package com.abdulaleem.i181570_i180514;

public class Messsage {
    private String message,senderId,messageId,imageUri;
    private long timeStamp;
    Messsage(){

    }
    public Messsage(String message, String senderId, long timeStamp) {

        this.message = message;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.messageId = messageId;
    }


    public String getMessageId() {
        return messageId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
