package com.mobdeve.ramos.isa;

public class Text {
    private String text;
    private String date;


    public Text(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public String getText_v() {
        return text;
    }

    public void setText_v(String text) {
        this.text = text;
    }

    public String getDate_v() {
        return date;
    }

    public void setdate_v(String text) {
        this.date = date;
    }


}

