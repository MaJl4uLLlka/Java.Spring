package com.example.mainspingproject.dto;

import com.example.mainspingproject.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthenticationRequestDTO {

    @EmailValid
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, max = 20,message = "password size must be in interval 5 - 20")
    private String password;
}
