package com.example.mainspingproject.dto;

import com.example.mainspingproject.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterNewRequestDTO {
    @Min(value = 1, message = "id is lower than 1")
    private Long meetup_id;

    @EmailValid
    private String requester_email;
}
