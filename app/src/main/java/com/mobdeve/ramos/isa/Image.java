package com.mobdeve.ramos.isa;

public class Image {
    private String imagename;
    private byte[] image;

    public Image(String imagename, byte[] image) {
        this.imagename = imagename;
        this.image = image;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
