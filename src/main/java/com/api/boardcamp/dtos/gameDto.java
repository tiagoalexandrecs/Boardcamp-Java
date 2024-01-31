package com.api.boardcamp.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;;

@Data
public class gameDto {
    
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String image;

    @NotBlank
    @NotNull
    @Positive
    private Integer stockTotal;

    @NotBlank
    @NotNull
    @Positive
    private Integer pricePerDay;
}
