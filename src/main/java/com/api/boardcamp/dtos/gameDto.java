package com.api.boardcamp.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class gameDto {
    
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String image;

    @NotNull
    @Positive
    private Integer stockTotal;

    @NotNull
    @Positive
    private Integer pricePerDay;

    public gameDto(String name, String image, Integer stockTotal, Integer pricePerDay) {
        this.name = name;
        this.image = image;
        this.stockTotal = stockTotal;
        this.pricePerDay = pricePerDay;
    }
}
