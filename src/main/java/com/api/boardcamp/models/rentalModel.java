package com.api.boardcamp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  
@Table(name = "rentals")
public class rentalModel {
    
    @Id // Identifica que é o id, a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.AUTO) // Estratégia gerar IDs
    private Long id;

    @Column(nullable= false)
    private Integer customerId;

    @Column(nullable= false)
    private Integer gameId;

    @Column(nullable= false)
    private Integer daysRented;

    @Column(nullable= false)
    private Integer originalPrice;

    @Column( columnDefinition = "string default null")
    private String returnDate;

    @Column( columnDefinition = "integer default 0")
    private Integer delayFee;
}
