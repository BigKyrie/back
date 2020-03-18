package com.example.movie.Service;

import com.example.movie.Entity.Screening;
import com.example.movie.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ScreeningService {
    @Autowired
    private ScreeningRepository screeningRepository;
    public List<Screening> display_screenings_by_id(Integer id){
        return screeningRepository.display_screenings_by_id(id);
    }
}
