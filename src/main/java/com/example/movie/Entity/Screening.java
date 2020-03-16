//package com.example.movie.Entity;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@Table(name = "Screening")
//public class Screening {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//    private Date time;
//    @ManyToMany
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "u_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "r_id", referencedColumnName = "id") )
//    private Set<Screen> screens;
//}
