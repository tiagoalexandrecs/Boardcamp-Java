package com.api.boardcamp.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class customerDto {
    
    public customerDto(String string, String string2) {
        this.name = string;
        this.cpf = string2;
    }

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    @Size(max=11, min= 11, message = "CPF must contain 11 characters, only the numbers are needed")
    private String cpf;
}
