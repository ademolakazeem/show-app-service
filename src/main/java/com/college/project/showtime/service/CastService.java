package com.college.project.showtime.service;

import com.college.project.showtime.model.Cast;
import com.college.project.showtime.model.Show;

import java.util.List;
import java.util.Set;

public interface CastService {
    Cast saveCast(String username, Long showId, Cast cast);

    List<Cast> getAllCasts(String username);

    Cast getOneCast(String username, String name);

    Set<Show> getCastShows(String username, Long id);
}
