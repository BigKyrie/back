package com.example.movie.Entity;

import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date create_time;
    private String type;
    private float price;
    //a user may have a lot of tickets, one ticket can only belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //a seat may relate to a lot of tickets, one ticket can only have one seat
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    //a screening may relate to a lot of tickets, one ticket can only relate to one screening
    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
