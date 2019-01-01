package com.college.project.showtime.controller;

import com.college.project.showtime.model.Genre;
import com.college.project.showtime.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> saveGenre(@RequestParam("showId") Long showId, @RequestParam("username") String username, @RequestBody Genre genre) {
        try {
            Genre savedGenre = genreService.saveGenre(username, showId, genre);
            if (savedGenre != null) {

                return ResponseEntity.created(new URI("/genres/" + +genre.getId())).build();
            }
            return (ResponseEntity<Genre>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Genre>> getAllGenres(@RequestParam("username") String username) {
        List<Genre> allGenres = genreService.getAllGenres(username);
        if (allGenres == null || allGenres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allGenres);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Genre> getOneGenre(@RequestParam("username") String username, @PathVariable("id") Long id) {
        Genre loadedGenre = genreService.getOneGenre(username, id);

        if (loadedGenre == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loadedGenre);
    }
}
