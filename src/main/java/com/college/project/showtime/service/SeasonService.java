package com.college.project.showtime.service;

import com.college.project.showtime.model.Season;

import java.util.List;

public interface SeasonService {
    Season saveSeason(String username, Long showId, Season season);

    List<Season> getAllSeasons(String username, Long showId);

    Season getOneSeason(String username, Long showId, Long id);

    Season updateSeason(String username, Long showId, Long id, Season season);
}
