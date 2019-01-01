package com.college.project.showtime.repository;

import com.college.project.showtime.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

}