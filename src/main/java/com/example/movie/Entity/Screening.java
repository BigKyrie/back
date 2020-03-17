package com.example.movie.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.sql.Time;
@Entity
@Table(name = "screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date start_time;
    private Date end_time;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
    //one user has many comments, one comment belong to one user

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }




}
