package com.example.mainspingproject.dto;

import com.example.mainspingproject.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterRequestDTO {

    @EmailValid
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 5, max = 20,message = "password size must be in interval 5 - 20")
    private String password;

    @NotBlank(message = "firstname is mandatory")
    @Size(min = 2, max = 50,message = "firstname size must be in interval 2 - 50")
    private String first_name;

    @NotBlank(message = "Lastname is mandatory")
    @Size(min = 2, max = 50,message = "lastname size must be in interval 2 - 50")
    private String last_name;
}
