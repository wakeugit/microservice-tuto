package com.microservice.microservice.dao;

import com.microservice.microservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    Product findById(int id);
    List<Product> findByPrixGreaterThan(int prix);
    List<Product> findByNomLike(String text);

    @Query("SELECT id, nom, prix FROM Product as p WHERE p.prix > :prixLimit")
    List<Product>  chercherUnProduitCher(@Param("prixLimit") int prix);


}

/*
    public List<Product> findAll();
    public Product findById(int id);
    public Product save(Product product);

    */