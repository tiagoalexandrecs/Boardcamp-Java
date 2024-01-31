package com.api.boardcamp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.boardcamp.models.customerModel;


@Repository
public interface customerRepository extends JpaRepository < customerModel, Long > {
    
    boolean existsById (Long id);
    boolean existsByCpf (String cpf);
    //@Query(value = " SELECT * from customers WHERE id = :id ", nativeQuery = true)
    //public Optional<customerModel> findById (@Param("id") Long id);
}
