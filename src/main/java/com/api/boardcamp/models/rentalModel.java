package com.api.boardcamp.models;

import java.util.Optional;

import com.api.boardcamp.dtos.rentalDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private Long customerId;

    @Column(nullable= false)
    private Long gameId;

    @Column(nullable= false)
    private Integer daysRented;

    @Column(nullable= false)
    private Integer originalPrice;

    @Column( columnDefinition = "string default null")
    private String returnDate;

    @Column( columnDefinition = "integer default 0")
    private Integer delayFee;

    @OneToOne
    @JoinColumn
    private customerModel customer;

    @OneToOne
    @JoinColumn
    private gameModel game;

    public rentalModel(rentalDto dto, gameModel game2, customerModel customer2, Integer price) {
        this.customerId = dto.getCustomerId();
        this.gameId = dto.getGameId();
        this.daysRented = dto.getDaysRented();
        this.customer = customer2;
        this.game = game2;
        this.originalPrice = price;
    }

}
