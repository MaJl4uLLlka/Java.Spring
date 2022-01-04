package com.example.mainspingproject.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ResponseMeetupDTO {
    private Long id;
    private String place;
    private LocalDate date;
    private LocalTime time;
    private String topic;
    private String requirements;
}
