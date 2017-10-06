package com.route.test.hungrydemo.model.db_shop;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by my301s on 2017/8/15.
 */

@Entity
public class Shop {
    @Id
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "img")
    private String img;
    @Property(nameInDb = "number")
    private int number;
    @Property(nameInDb = "price")
    private double price;
    @Generated(hash = 1097395711)
    public Shop(Long id, String name, String img, int number, double price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.number = number;
        this.price = price;
    }
    @Generated(hash = 633476670)
    public Shop() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public int getNumber() {
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
