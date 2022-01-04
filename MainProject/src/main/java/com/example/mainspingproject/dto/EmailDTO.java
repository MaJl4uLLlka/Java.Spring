package com.example.mainspingproject.dto;

import com.example.mainspingproject.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EmailDTO {
    @EmailValid
    private String email;
}
