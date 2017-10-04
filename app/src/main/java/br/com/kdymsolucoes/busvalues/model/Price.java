package br.com.kdymsolucoes.busvalues.model;

import java.util.Date;

/**
 * Created by sKnMetal on 04/10/2017.
 */

public class Price {
    private Date date;
    private String category;
    private double price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
