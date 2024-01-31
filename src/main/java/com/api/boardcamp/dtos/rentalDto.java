package com.api.boardcamp.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class rentalDto {

    @NotBlank
    @NotNull
    @Positive
    private Integer customerId;

    @NotBlank
    @NotNull
    @Positive
    private Integer gameId;

    @NotBlank
    @NotNull
    @Positive
    private Integer daysRented;



    
}