//package com.example.movie.Entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "Screen")
//public class Screen {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "Cinema_id")
//    private User user;
//
//    @JsonIgnore
//    @ManyToMany(mappedBy = "screenings")
//    private Set<Screening> screenings;
//}
