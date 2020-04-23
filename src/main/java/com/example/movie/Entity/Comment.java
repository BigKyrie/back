package com.example.movie.Entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer grade;
    private String comment;

    @ManyToOne//one user has many comments, one comment belong to one user
    @JoinColumn(name = "user_id")
    private Cinema_Admin cinema_admin;
    @ManyToOne//one movie has many comments, one comment belong to one movie
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Cinema_Admin getUser() {
        return cinema_admin;
    }

    public void setUser(Cinema_Admin cinema_admin) {
        this.cinema_admin = cinema_admin;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }



}