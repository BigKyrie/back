package com.example.movie.Entity;

import javax.persistence.*;

@Entity
@Table(name = "seat" )
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer row;
    private Integer col;

    //There are a lot of seats in one screen, one seat may belong to one screen
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    //getters and setters
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

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
