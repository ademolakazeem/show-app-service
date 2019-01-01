package com.college.project.showtime.repository;

import com.college.project.showtime.model.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long> {
    Cast findByName(String name);
}
