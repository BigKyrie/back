package com.example.movie.Entity;

import javax.persistence.*;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "card_number")
    private String number;
    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Cinema_Admin cinema_admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cinema_Admin getCinema_admin() {
        return cinema_admin;
    }

    public void setCinema_admin(Cinema_Admin cinema_admin) {
        this.cinema_admin = cinema_admin;
    }


}
