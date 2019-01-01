package com.college.project.showtime.controller;

import com.college.project.showtime.model.Show;
import com.college.project.showtime.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Show> saveShow(@RequestParam("username") String username, @RequestBody Show show) {
        try {
            if (showService.saveShow(username, show) != null) {

                return ResponseEntity.created(new URI("/shows/" + show.getId())).build();
            }
            return (ResponseEntity<Show>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{showName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Show> getShowByShowName(@RequestParam("username") String username, @PathVariable("showName") String showName) {
        Show show = showService.getShowByShowName(username, showName);

        if (show == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(show);
    }

    @GetMapping(value = "/startDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Show>> getShowByStartDate(@RequestParam("username") String username, @RequestParam("startDate") String startDate) {

        Date sDate = null;
        try {
            sDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Show> shows = showService.getShowByStartdate(username, sDate);

        if (shows == null || shows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(shows);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Show>> getAllShows(@RequestParam("username") String username) {
        List<Show> shows = showService.getAllShows(username);

        if (shows == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(shows);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateShow(@RequestParam("username") String username, @PathVariable("id") Long id, @RequestBody Show show) {

        if (Long.valueOf(id).equals(show.getId())) {
            Show loadedShow = showService.updateShow(username, id, show);
            if (loadedShow != null) {
                return ResponseEntity.accepted().build();
            }
            return (ResponseEntity<Show>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Looks like you're trying to save a different show!");
        }
    }

}
