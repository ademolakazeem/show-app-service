package com.college.project.showtime.controller;

import com.college.project.showtime.model.Season;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/shows/{showId}/seasons")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Season>> getAllSeasons(@RequestParam("username") String username, @PathVariable("showId") Long showId) {
        List<Season> allSeasons = seasonService.getAllSeasons(username, showId);
        if (allSeasons == null || allSeasons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allSeasons);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Show> saveSeason(@PathVariable("showId") Long showId, @RequestParam("username") String username, @RequestBody Season season) throws URISyntaxException {
        try {

            Season savedSeason = seasonService.saveSeason(username, showId, season);
            if (savedSeason != null) {
                return ResponseEntity.created(new URI("/shows/" + savedSeason.getShow().getId() + "/seasons/" + savedSeason.getId())).build();
            }
            return (ResponseEntity<Show>) ResponseEntity.status(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Season> getOneSeason(@PathVariable("showId") Long showId, @RequestParam("username") String username, @PathVariable("id") Long id) {
        Season loadedSeason = seasonService.getOneSeason(username, showId, id);

        if (loadedSeason == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loadedSeason);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSeason(@RequestParam("username") String username, @PathVariable("showId") Long showId, @PathVariable("id") Long id, @RequestBody Season season) {

        if (Long.valueOf(id).equals(season.getId())) {
            Season loadedSeason = seasonService.updateSeason(username, showId, id, season);
            if (loadedSeason != null) {
                return ResponseEntity.accepted().build();
            }
            return (ResponseEntity<Show>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Looks like you're trying to save a different season!");
        }
    }
}
