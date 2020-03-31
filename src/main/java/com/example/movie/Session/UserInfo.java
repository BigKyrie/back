package com.example.movie.Session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

    private int userId;
    private String username;

    public UserInfo(Integer id, String username) {
        this.userId=id;
        this.username=username;
    }
}
