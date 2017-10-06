package com.route.test.hungrydemo.event;

/**
 * Created by my301s on 2017/8/19.
 */

public class Image {
    String img;

    public Image(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Image{" +
                "img='" + img + '\'' +
                '}';
    }
}
