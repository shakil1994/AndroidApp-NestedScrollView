package com.example.shakil.androidrclnested.Model;

public class Model {
    private String image_link, text;

    public Model() {
    }

    public Model(String image_link, String text) {
        this.image_link = image_link;
        this.text = text;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
