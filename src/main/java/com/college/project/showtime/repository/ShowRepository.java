package com.college.project.showtime.repository;

import com.college.project.showtime.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByStatus(String name);

    Show findById(Long id);


}
