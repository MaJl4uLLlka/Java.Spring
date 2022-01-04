package com.example.mainspingproject.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SearchWithFilterDTO {
    private LocalDate date;
    private LocalTime start_time;
    private LocalTime end_time;
}
