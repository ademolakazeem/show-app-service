package com.college.project.showtime.service;

import com.college.project.showtime.model.Show;
import com.college.project.showtime.model.User;
import com.college.project.showtime.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    UserService userService;

    @Autowired
    ShowRepository showRepository;

    @Override
    public Show saveShow(String username, Show show) {
        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            return showRepository.save(show);
        }
        return null;
    }


    @Override
    public Show getShowByShowName(String username, String showName) {

        User userFound = userService.findUserByName(username);

        Show show = new Show();
        show.setName(showName);
        Show loadedShow = null;
        if (userFound != null && userFound.isAdmin()) {
            loadedShow = showRepository.findOne(Example.of(show));
        } else if (userFound != null && !userFound.isAdmin()) {
            show.setStatus("public");
            loadedShow = showRepository.findOne(Example.of(show));
        }
        return loadedShow;

    }

    @Override
    public List<Show> getAllShows(String username) {

        List<Show> allShows = null;
        User userFound = userService.findUserByName(username);

        if (userFound != null && userFound.isAdmin()) {
            allShows = showRepository.findAll();
        } else if (userFound != null && !userFound.isAdmin()) {
            allShows = showRepository.findByStatus("public");
        }

        return allShows;
    }

    @Override
    public Show updateShow(String username, Long id, Show show) {

        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            Show loadedShow = showRepository.findOne(id);
            if (loadedShow != null) {
                loadedShow.setName(show.getName());
                loadedShow.setCountry(show.getCountry());
                loadedShow.setStatus(show.getStatus());
                loadedShow.setDescription(show.getDescription());
                loadedShow.setEndDate(show.getEndDate());
                loadedShow.setStartDate(show.getStartDate());
                loadedShow.setNetwork(show.getNetwork());
                loadedShow.setRuntime(show.getRuntime());
                loadedShow.setImagePath(show.getImagePath());
                return showRepository.save(loadedShow);
            }
        }
        return null;
    }


    @Override
    public List<Show> getShowByGenre(String genreName) {
        return null;
    }

    @Override
    public List<Show> getShowByStartdate(String username, Date startDate) {
        List<Show> allShows = null;
        Show show = new Show();
        User userFound = userService.findUserByName(username);
        show.setStartDate(startDate);
        if (userFound != null && userFound.isAdmin()) {
            allShows = showRepository.findAll(Example.of(show));
        } else if (userFound != null && !userFound.isAdmin()) {
            show.setStatus("public");
            allShows = showRepository.findAll(Example.of(show));
        }

        return allShows;
    }

}
