package com.olivier.jasmedapp.model;

public class Mail {
    private String from;
    private String title;
    private String body;

    public Mail() {
    }

    public Mail(String from, String title, String body) {
        this.from = from;
        this.title = title;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
