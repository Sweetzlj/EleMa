package com.route.test.hungrydemo.model.bean;

/**
 * Created by my301s on 2017/8/14.
 */

public class GouWu {
    private int position;
    private int number;

    public GouWu(int position, int number) {
        this.position = position;
        this.number = number;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GouWu{" +
                "position=" + position +
                ", number=" + number +
                '}';
    }
}
