package com.college.project.showtime.service;

import com.college.project.showtime.model.Season;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.model.User;
import com.college.project.showtime.repository.SeasonRepository;
import com.college.project.showtime.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {
    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserService userService;

    @Override
    public Season saveSeason(String username, Long showId, Season season) {
        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            Show show = showRepository.findOne(showId);
            if (show != null) {
                season.setShow(show);
                return seasonRepository.save(season);
            }
        }

        return null;
    }

    @Override
    public List<Season> getAllSeasons(String username, Long showId) {
        Show show = new Show();
        Season season = new Season();
        show.setId(showId);
        List<Season> allSeasons = null;

        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            season.setShow(show);
            allSeasons = seasonRepository.findAll(Example.of(season));
        } else if (userFound != null && !userFound.isAdmin()) {
            show.setStatus("public");
            season.setShow(show);
            allSeasons = seasonRepository.findAll(Example.of(season));
        }
        return allSeasons;
    }

    @Override
    public Season getOneSeason(String username, Long showId, Long id) {
        Show show = new Show();
        Season season = new Season();
        season.setId(id);
        show.setId(showId);

        Season loadedSeason = null;
        User userFound = userService.findUserByName(username);

        if (userFound != null && userFound.isAdmin()) {
            season.setShow(show);
            loadedSeason = seasonRepository.findOne(Example.of(season));
        } else if (userFound != null && !userFound.isAdmin()) {
            show.setStatus("public");
            season.setShow(show);
            loadedSeason = seasonRepository.findOne(Example.of(season));
        }
        return loadedSeason;
    }

    @Override
    public Season updateSeason(String username, Long showId, Long id, Season season) {

        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            Season loadedSeason = seasonRepository.findOne(id);
            if (loadedSeason != null) {
                loadedSeason.setSeasonNumber(season.getSeasonNumber());
                loadedSeason.setFirstAired(season.getFirstAired());
                loadedSeason.setLastAired(season.getLastAired());
                return seasonRepository.save(loadedSeason);
            }
        }
        return null;
    }
}

