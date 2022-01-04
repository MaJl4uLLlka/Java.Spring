package com.example.mainspingproject.dto;

import com.example.mainspingproject.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestMeetupDTO {
    @NotBlank(message = "Place is mandatory")
    @Size(min = 5, max = 100,message = "place size must be in interval 5 - 100")
    private String place;

    private LocalDate date;
    private LocalTime time;

    @NotBlank(message = "Email is mandatory")
    @Size(min = 2, max = 100,message = "topic size must be in interval 5 - 100")
    private String topic;

    @NotBlank(message = "Requirements is mandatory")
    @Size(min = 4, max = 255,message = "requirements size must be in interval 4 - 255")
    private String requirements;

    @EmailValid
    private String creator_email;
}
