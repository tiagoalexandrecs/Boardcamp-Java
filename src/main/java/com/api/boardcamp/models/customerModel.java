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
@Table(name = "customers")
public class customerModel {
    
    @Id // Identifica que é o id, a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Estratégia gerar IDs
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length= 11 , nullable = false)
    private String cpf;


}
