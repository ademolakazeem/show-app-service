package com.college.project.showtime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "episodes")
public class Episode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String name;

    @Column(name = "aired_date")
    private Date airedDate;

    @ManyToOne
    @JoinColumn(name = "season_id")
    @JsonBackReference
    private Season season;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAiredDate() {
        return airedDate;
    }

    public void setAiredDate(Date airedDate) {
        this.airedDate = airedDate;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
