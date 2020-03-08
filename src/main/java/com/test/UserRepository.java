package com.test;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>
{
    @Query(value = "select * from user where username = ?",nativeQuery = true)
    public List<User> findByUsername(String username);
    @Query(value = "select * from user where username=? and password=?",nativeQuery = true)
    public List<User> findByUsernameAndPassword(String username,String password);

    @Modifying //may delete
    @Query(value = "delete from user where name = ?1",nativeQuery = true)  //may delete
    public int deleteByName(String name);  //may delete
}
