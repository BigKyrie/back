package com.example.movie.Service;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Comment;
import com.example.movie.Entity.Movie;
import com.example.movie.Repository.Cinema_AdminRepository;
import com.example.movie.Repository.CommentRepository;
import com.example.movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private Cinema_AdminService cinema_adminService;

    public List<Comment> find_by_movie_id(Integer id){
        return commentRepository.findByMovie(id);
    }

    public List<Comment> find_by_movie_and_user(Integer movie_id, Integer user_id){
        return commentRepository.findByUserandMovie(user_id,movie_id);
    }

    public Comment findbyID(Integer id){
        return commentRepository.findByid(id);
    }

    public List<Comment> findbyUserid(Integer id){
        return commentRepository.findByUser(id);
    }

    public void delete_by_comment_id(Integer id){
        commentRepository.delete_by_comment_id(id);
    }

    public boolean add(String comment, Integer grade, Integer movie_id, Integer user_id){
        List<Comment> comments = commentRepository.findByUserandMovie(user_id,movie_id);
        Cinema_Admin cinema_admin = cinema_adminService.findAdminById(user_id);
        Movie movie = movieService.findMoviebyID(movie_id);

        if(comments.size()>0){
            return false;
        }
        else{
            if(comment.length()>100){
                return false;
            }
            else{
                Comment comment1 = new Comment();
                comment1.setComment(comment);
                comment1.setGrade(grade);
                comment1.setMovie(movie);
                comment1.setUser(cinema_admin);
                commentRepository.save(comment1);
                return  true;
            }
        }

    }

    @Transactional
    public void update(String comment, Integer grade, Integer comment_id){
        Comment comment1 = this.findbyID(comment_id);
        comment1.setComment(comment);
        comment1.setGrade(grade);
    }

}
