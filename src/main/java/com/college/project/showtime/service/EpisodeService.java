package com.college.project.showtime.service;

import com.college.project.showtime.model.Episode;

import java.util.List;

public interface EpisodeService {
    Episode saveEpisode(String username, Long showId, Long seasonId, Episode episode);

    Episode getOneEpisode(String username, Long showId, Long seasonId, Long id);

    List<Episode> getAllEpisodes(String username, Long showId, Long seasonId);

    Episode updateEpisode(String username, Long showId, Long seasonId, Long id, Episode episode);
}
