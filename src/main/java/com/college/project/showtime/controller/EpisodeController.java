package com.college.project.showtime.controller;

import com.college.project.showtime.model.Episode;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/shows/{showId}/seasons/{seasonId}/episodes")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Episode>> getAllEpisodes(@RequestParam("username") String username, @PathVariable("showId") Long showId, @PathVariable("seasonId") Long seasonId) {
        List<Episode> allEpisodes = episodeService.getAllEpisodes(username, showId, seasonId);
        if (allEpisodes == null || allEpisodes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allEpisodes);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Show> saveEpisode(@PathVariable("showId") Long showId, @PathVariable("seasonId") Long seasonId, @RequestParam("username") String username, @RequestBody Episode episode) throws URISyntaxException {
        try {

            Episode savedEpisode = episodeService.saveEpisode(username, showId, seasonId, episode);
            if (savedEpisode != null) {
                return ResponseEntity.created(new URI("/shows/" + savedEpisode.getSeason().getShow().getId() + "/seasons/" + savedEpisode.getSeason().getId() + "/episodes/" +
                        savedEpisode.getId())).build();
            }
            return (ResponseEntity<Show>) ResponseEntity.status(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Episode> getOneEpisode(@PathVariable("showId") Long showId, @RequestParam("username") String username, @PathVariable("seasonId") Long seasonId, @PathVariable("id") Long id) {
        Episode loadedEpisode = episodeService.getOneEpisode(username, showId, seasonId, id);

        if (loadedEpisode == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loadedEpisode);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEpisode(@RequestParam("username") String username, @PathVariable("showId") Long showId, @PathVariable("seasonId") Long seasonId, @PathVariable("id") Long id, @RequestBody Episode episode) {

        if (Long.valueOf(id).equals(episode.getId())) {
            Episode loadedEpisode = episodeService.updateEpisode(username, showId, seasonId, id, episode);
            if (loadedEpisode != null) {
                return ResponseEntity.accepted().build();
            }
            return (ResponseEntity<Show>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Looks like you're trying to save a different season!");
        }
    }

}
