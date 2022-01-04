package com.example.mainspingproject.dto;

import com.example.mainspingproject.validator.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RequestReportDTO {
    @NotBlank(message = "Topic is mandatory")
    @Size(min = 2, max = 100,message = "topic size must be in interval 5 - 100")
    private String topic;

    @EmailValid
    private String reporter;
}
