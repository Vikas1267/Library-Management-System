package com.library.librarymanagement.controller.dto;

public class AiChatRequest {

    private String message;
    private String topic;

    public AiChatRequest() {}

    public AiChatRequest(String message, String topic) {
        this.message = message;
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public String getTopic() {
        return topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
