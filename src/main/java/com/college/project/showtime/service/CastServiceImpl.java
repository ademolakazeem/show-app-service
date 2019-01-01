package com.college.project.showtime.service;

import com.college.project.showtime.model.Cast;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.model.User;
import com.college.project.showtime.repository.CastRepository;
import com.college.project.showtime.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CastServiceImpl implements CastService {

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private UserService userService;

    @Override
    public Cast saveCast(String username, Long showId, Cast cast) {
        Set<Show> shows = new HashSet<>();
        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            Show show = showRepository.findById(showId);
            if (show != null) {

                if (castRepository.findByName(cast.getName()) == null) {
                    shows.add(show);
                    cast.setShows(shows);
                    return castRepository.save(cast);

                }
            }
            return null;
        }
        return null;
    }

    @Override
    public List<Cast> getAllCasts(String username) {
        List<Cast> allCasts = null;
        User userFound = userService.findUserByName(username);
        if (userFound != null) {
            allCasts = castRepository.findAll();
        }
        return allCasts;
    }

    @Override
    public Cast getOneCast(String username, String name) {
        Cast loadedCast = null;
        User userFound = userService.findUserByName(username);
        if (userFound != null) {
            loadedCast = castRepository.findByName(name);
        }
        return loadedCast;
    }

    @Override
    public Set<Show> getCastShows(String username, Long id) {
        Set<Show> loadedShows = null;
        User userFound = userService.findUserByName(username);
        if (userFound != null) {
            Cast loadedCast = castRepository.findOne(id);
            if (loadedCast != null) {
                loadedShows = loadedCast.getShows();
            }
        }
        return loadedShows;
    }

}
