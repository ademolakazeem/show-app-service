package com.college.project.showtime.service;

import com.college.project.showtime.model.Genre;

import java.util.List;

public interface GenreService {
    Genre saveGenre(String username, Long showId, Genre genre);

    List<Genre> getAllGenres(String username);

    Genre getOneGenre(String username, Long id);
}
