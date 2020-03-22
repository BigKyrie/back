package com.example.movie.Repository;

import com.example.movie.Entity.Cinema_Admin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cinema_AdminRepository extends CrudRepository<Cinema_Admin,Integer>
{
    @Query(value = "select * from cinema_admin where username = ?",nativeQuery = true)
    public List<Cinema_Admin> findByCinemaAdminUsername(String username);

    @Query(value = "select * from cinema_admin where username=? and password=?",nativeQuery = true)
    public List<Cinema_Admin> findByCinemaAdminUsernameAndPassword(String username,String password);

    @Modifying //may delete
    @Query(value = "delete from cinema_admin where name = ?1",nativeQuery = true)  //may delete
    public int deleteByName(String name);  //may delete

}
