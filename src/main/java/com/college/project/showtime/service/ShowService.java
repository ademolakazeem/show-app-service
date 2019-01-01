package com.college.project.showtime.service;

import com.college.project.showtime.model.Show;

import java.util.Date;
import java.util.List;

public interface ShowService {
    Show saveShow(String username, Show show);

    Show getShowByShowName(String username, String showName);

    List<Show> getAllShows(String username);

    Show updateShow(String username, Long id, Show show);


    List<Show> getShowByGenre(String genreName);

    List<Show> getShowByStartdate(String username, Date startDate);
}
