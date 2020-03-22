package com.example.movie.Form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class MovieForm {
    @Length(min = 1, max = 20, message = "Between 1-20 characters")
    @NotEmpty(message="Cannot be empty")
    private String title;
    @Length(min = 1, max = 140, message = "Between 1-140 characters")
    @NotEmpty(message="Cannot be empty")
    private String blurb;
    @Length(min = 1, max = 10, message = "Between 1-10 characters")
    @NotEmpty(message="Cannot be empty")
    private String certificate;
    @Length(min = 1, max = 100, message = "Between 1-100 characters")
    @NotEmpty(message="Cannot be empty")
    private String director;
    @Length(min = 1, max = 100, message = "Between 1-100 characters")
    @NotEmpty(message="Cannot be empty")
    private String actors;
    @NotEmpty(message="Cannot be empty")
    private Date showtime;
    @Min(1)
    @NotEmpty(message="Cannot be empty")
    private Integer duration;
    @Length(min = 1, max = 100, message = "Between 1-100 characters")
    @NotEmpty(message="Cannot be empty")
    private String type;
    @NotEmpty(message="Cannot be empty")
    @Length(min = 1, max = 20, message = "Between 1-20 characters")
    private String language;
    @Length(min = 1, max = 1000, message = "Between 1-1000 characters")
    @NotEmpty(message="Cannot be empty")
    private String url;

}
