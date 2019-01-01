package com.college.project.showtime.service;

import com.college.project.showtime.model.Episode;
import com.college.project.showtime.model.Season;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.model.User;
import com.college.project.showtime.repository.EpisodeRepository;
import com.college.project.showtime.repository.SeasonRepository;
import com.college.project.showtime.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private UserService userService;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Override
    public Episode saveEpisode(String username, Long showId, Long seasonId, Episode episode) {
        Show show = showRepository.findOne(showId);
        Season newSeason = seasonRepository.findOne(seasonId);
        if (show != null && newSeason != null) {
            newSeason.setShow(show);
            episode.setSeason(newSeason);
            return episodeRepository.save(episode);
        }

        return null;
    }

    @Override
    public List<Episode> getAllEpisodes(String username, Long showId, Long seasonId) {
        Show show = new Show();
        Season season = new Season();
        Episode episode = new Episode();
        show.setId(showId);
        List<Episode> allEpisodes = null;
        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            season.setShow(show);
            episode.setSeason(season);
            allEpisodes = episodeRepository.findAll(Example.of(episode));
        } else if (userFound != null && !userFound.isAdmin()) {
            show.setStatus("public");
            season.setShow(show);
            episode.setSeason(season);
            allEpisodes = episodeRepository.findAll(Example.of(episode));
        }
        return allEpisodes;
    }

    @Override
    public Episode getOneEpisode(String username, Long showId, Long seasonId, Long id) {
        Show show = new Show();
        Season season = new Season();
        Episode episode = new Episode();
        episode.setId(id);
        season.setId(seasonId);
        show.setId(showId);

        Episode loadedEpisode = null;

        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            season.setShow(show);
            episode.setSeason(season);
            loadedEpisode = episodeRepository.findOne(Example.of(episode));
        } else if (userFound != null && !userFound.isAdmin()) {
            show.setStatus("public");
            season.setShow(show);
            episode.setSeason(season);
            loadedEpisode = episodeRepository.findOne(Example.of(episode));
        }
        return loadedEpisode;
    }

    @Override
    public Episode updateEpisode(String username, Long showId, Long seasonId, Long id, Episode episode) {

        User userFound = userService.findUserByName(username);
        if (userFound != null && userFound.isAdmin()) {
            Episode loadedEpisode = episodeRepository.findOne(id);
            if (loadedEpisode != null) {
                loadedEpisode.setName(episode.getName());
                loadedEpisode.setAiredDate(episode.getAiredDate());
                return episodeRepository.save(loadedEpisode);
            }
        }
        return null;
    }
}
