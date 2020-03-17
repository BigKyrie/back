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
    private Screening screening;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public boolean isTaken_or_not() {
        return taken_or_not;
    }

    public void setTaken_or_not(boolean taken_or_not) {
        this.taken_or_not = taken_or_not;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }



}
