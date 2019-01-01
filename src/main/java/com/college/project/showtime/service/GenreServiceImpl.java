package com.college.project.showtime.service;

import com.college.project.showtime.model.Genre;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.model.User;
import com.college.project.showtime.repository.GenreRepository;
import com.college.project.showtime.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private UserService userService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ShowRepository showRepository;

    @Override
    public Genre saveGenre(String username, Long showId, Genre genre) {
        Set<Show> shows = new HashSet<>();
        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            Show show = showRepository.findById(showId);
            if (show != null) {
                shows.add(show);
                genre.setShows(shows);
                return genreRepository.save(genre);
            }
            return null;
        }
        return null;
    }

    @Override
    public List<Genre> getAllGenres(String username) {
        List<Genre> allCasts = null;
        User userFound = userService.findUserByName(username);
        if (userFound != null) {
            allCasts = genreRepository.findAll();
        }
        return allCasts;
    }

    @Override
    public Genre getOneGenre(String username, Long id) {
        Genre loadedCast = null;
        User userFound = userService.findUserByName(username);
        if (userFound != null) {
            loadedCast = genreRepository.findOne(id);
        }
        return loadedCast;
    }
}
