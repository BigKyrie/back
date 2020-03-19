package com.example.movie.Service;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.User;
import com.example.movie.Repository.Cinema_AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class Cinema_AdminService {
    @Autowired
    private Cinema_AdminRepository cinema_adminRepository;

    public List<Cinema_Admin> findByCinemaAdminUsernameAndPassword(String username,String password){
        return cinema_adminRepository.findByCinemaAdminUsernameAndPassword(username,password);

    }

    //add a cinema admin
    public boolean add(String username, String password) {
        List<Cinema_Admin> findCinemaAdminUserName = cinema_adminRepository.findByCinemaAdminUsername(username);
        if (findCinemaAdminUserName.size() != 0) {
            return false;
        } else {
            if (username.length() > 20 || username.length() == 0 || password.length() > 20 || password.length() == 0) {
                return false;
            } else {
                Cinema_Admin cinema_admin = new Cinema_Admin();
                cinema_admin.setUsername(username);
                cinema_admin.setPassword(password);
                cinema_adminRepository.save(cinema_admin);
                return true;
            }
        }
    }
}
