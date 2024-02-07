package com.api.boardcamp.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class rentalDto {

    public rentalDto(Long customerId, Long gameId, Integer daysRented) {
        this.customerId = customerId;
        this.gameId = gameId;
        this.daysRented = daysRented;
    }


    @NotNull
    @Positive
    private Long customerId;


    @NotNull
    @Positive
    private Long gameId;

    @NotNull
    @Positive
    private Integer daysRented;



    
}
