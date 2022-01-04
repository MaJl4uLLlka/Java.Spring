package com.example.mainspingproject.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class FilterDTO {
    private LocalTime start;
    private LocalTime end;
}
