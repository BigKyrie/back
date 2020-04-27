package com.example.movie.Repository;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Comment;
import com.example.movie.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
    @Query(value = "select * from comment where user_id=? and movie_id=?",nativeQuery = true)
    public List<Comment> findByUserandMovie(Integer user_id, Integer movie_id);
    @Query(value = "select * from comment where movie_id=?",nativeQuery = true)
    public List<Comment> findByMovie(Integer movie_id);
    @Query(value = "select * from comment where id=?",nativeQuery = true)
    public Comment findByid(Integer comment_id);
    @Query(value = "select * from comment where user_id=?",nativeQuery = true)
    public List<Comment> findByUser(Integer user_id);

    @Modifying
    @Query(value = "delete from comment where id = ?",nativeQuery = true)  //may delete
    public void delete_by_comment_id(Integer id);


}
