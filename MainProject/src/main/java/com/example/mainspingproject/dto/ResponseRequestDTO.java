package com.example.mainspingproject.dto;

import lombok.Data;

@Data
public class ResponseRequestDTO {
    private Long id;
    private Boolean isCanceled;
    private Boolean isApproved;
}
