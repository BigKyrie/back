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

}
