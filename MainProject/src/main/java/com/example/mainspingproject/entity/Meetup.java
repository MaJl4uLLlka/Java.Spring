package com.example.mainspingproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@Table(name = "meetups")
public class Meetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "place", nullable = false, length = 100)
    private String place;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "topic", nullable = false, length = 100)
    private String topic;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "creator_email")
    private String creator;

    @OneToMany(mappedBy = "meetup")
    private Set<Request> requestSet;
}