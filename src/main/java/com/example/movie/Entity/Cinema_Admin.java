package com.example.movie.Entity;

import javax.persistence.*;

    @Entity
    @Table(name = "cinema_admin")
    public class Cinema_Admin {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Integer id;
        @Column(name = "username")
        private String username;
        @Column(name = "password")
        private String password;

        @OneToOne
        @JoinColumn(name = "cinema_id")
        private Cinema cinema;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Cinema getCinema() {
            return cinema;
        }

        public void setCinema(Cinema cinema) {
            this.cinema = cinema;
        }
    }
