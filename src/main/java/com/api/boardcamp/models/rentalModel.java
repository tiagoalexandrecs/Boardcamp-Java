package com.api.boardcamp.models;

import java.time.LocalDate;

import com.api.boardcamp.dtos.rentalDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  
@Table(name="rentals")
public class rentalModel {
    
    @Id // Identifica que é o id, a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.AUTO) // Estratégia gerar IDs
    private Long id;

    @Column(nullable= false)
    private Long customerid;

    @Column(nullable= false)
    private Long gameid;

    @Column(nullable= false)
    private Integer daysRented;

    @Column(nullable= false)
    private Integer originalPrice;

    @Column(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate= null;

    @Column(nullable= false)
    private Long delayFee = 0L;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rentDate;

    @ManyToOne
    @JoinColumn(name= "customer")
    private customerModel customer;

    @ManyToOne
    @JoinColumn(name= "game")
    private gameModel game;

    public rentalModel(rentalDto dto, gameModel game2, customerModel customer2, Integer price, LocalDate date) {
        this.customerid= dto.getCustomerId();
        this.gameid= dto.getGameId();
        this.daysRented = dto.getDaysRented();
        this.customer = customer2;
        this.game = game2;
        this.originalPrice = price;
        this.rentDate = date;
    }

    public rentalModel (rentalModel rent, LocalDate date) {
        this.customerid = rent.getCustomerid();
        this.gameid= rent.getGameid();
        this.daysRented = rent.getDaysRented();
        this.customer = rent.getCustomer();
        this.game = rent.getGame();
        this.originalPrice = rent.getOriginalPrice();
        this.rentDate = rent.getRentDate();
        this.returnDate = date;
    }

    public rentalModel (rentalModel rent, LocalDate date, Long debt) {
        this.customerid = rent.getCustomerid();
        this.gameid = rent.getGameid();
        this.daysRented = rent.getDaysRented();
        this.customer = rent.getCustomer();
        this.game = rent.getGame();
        this.originalPrice = rent.getOriginalPrice();
        this.rentDate = rent.getRentDate();
        this.returnDate = date;
        this.delayFee = debt;
    }

}
