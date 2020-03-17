package com.example.movie.Entity;

import javax.persistence.*;

@Entity
@Table(name = "seat" )
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer row;
    private Integer column;
    private boolean taken_or_not;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screen screen;
}
