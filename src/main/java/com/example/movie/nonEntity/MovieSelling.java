package com.example.movie.nonEntity;

import java.util.Date;

public class MovieSelling {
    private Integer id;
    private String url;
    private String title;
    private String certificate;
    private Date showtime;
    private String type;
    private Integer selling_num_week;
    private Integer selling_num_month;
    private Integer selling_num_total;
    private float money_week;
    private float money_month;
    private float money_total;

    public Integer getId() {
        return id;
    }

    public Date getShowtime() {
        return showtime;
    }

    public float getMoney_month() {
        return money_month;
    }

    public float getMoney_total() {
        return money_total;
    }

    public float getMoney_week() {
        return money_week;
    }

    public Integer getSelling_num_month() {
        return selling_num_month;
    }

    public Integer getSelling_num_total() {
        return selling_num_total;
    }

    public Integer getSelling_num_week() {
        return selling_num_week;
    }

    public String getCertificate() {
        return certificate;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public void setMoney_month(float money_month) {
        this.money_month = money_month;
    }

    public void setMoney_total(float money_total) {
        this.money_total = money_total;
    }

    public void setMoney_week(float money_week) {
        this.money_week = money_week;
    }

    public void setSelling_num_month(Integer selling_num_month) {
        this.selling_num_month = selling_num_month;
    }

    public void setSelling_num_total(Integer selling_num_total) {
        this.selling_num_total = selling_num_total;
    }

    public void setSelling_num_week(Integer selling_num_week) {
        this.selling_num_week = selling_num_week;
    }

    public void setShowtime(Date showtime) {
        this.showtime = showtime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
