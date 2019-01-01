package com.college.project.showtime.controller;

import com.college.project.showtime.model.Cast;
import com.college.project.showtime.model.Show;
import com.college.project.showtime.service.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/casts")
public class CastController {

    @Autowired
    private CastService castService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cast> saveCast(@RequestParam("username") String username, @RequestParam("showId") Long showId, @RequestBody Cast cast) {
        try {
            Cast savedCast = castService.saveCast(username, showId, cast);
            if (savedCast != null) {

                return ResponseEntity.created(new URI("/casts/" + cast.getId())).build();
            }
            return (ResponseEntity<Cast>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cast>> getAllCasts(@RequestParam("username") String username) {
        List<Cast> allCasts = castService.getAllCasts(username);
        if (allCasts == null || allCasts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allCasts);
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cast> getOneCast(@RequestParam("username") String username, @PathVariable("name") String name) {
        Cast loadedCast = castService.getOneCast(username, name);

        if (loadedCast == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loadedCast);
    }

    @GetMapping(value = "/{id}/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Show>> getCastShows(@RequestParam("username") String username, @PathVariable("id") Long id) {
        Set<Show> castShows = castService.getCastShows(username, id);

        if (castShows == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(castShows);
    }

}
