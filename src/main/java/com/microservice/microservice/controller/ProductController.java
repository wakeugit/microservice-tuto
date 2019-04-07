package com.microservice.microservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.microservice.microservice.dao.ProductDao;
import com.microservice.microservice.exception.ProductNotFoundException;
import com.microservice.microservice.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(description = "Products management")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Produits

    //@RequestMapping(value ="Produits", method = RequestMethod.GET)
    @ApiOperation("List all products")
    @GetMapping(value = "Produits")
    public List<Product> getProducts(){
        return productDao.findAll();
    }
    /*public MappingJacksonValue getProducts(){
        List<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listeFilters = new SimpleFilterProvider().addFilter("monFiltreDynamique", myFilter);

        MappingJacksonValue jacksonValue = new MappingJacksonValue(products);
        jacksonValue.setFilters(listeFilters);

        return jacksonValue;
    }*/

    //Produits/prix/{prixLimit}
    @GetMapping(value ="Produits/prix/{prixLimit}")
    public List<Product> getProductsPrixgreaterThan(@PathVariable int prixLimit){
        return productDao.findByPrixGreaterThan(prixLimit);
    }
    /*@GetMapping(value ="Produits/prix/{prixLimit}")
    public MappingJacksonValue getProductsPrixgreaterThan(int prixLimit){
        List<Product> products = productDao.findByPrixGreaterThan(prixLimit);

        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listeFilters = new SimpleFilterProvider().addFilter("monFIltreDynamique", myFilter);

        MappingJacksonValue jacksonValue = new MappingJacksonValue(products);
        jacksonValue.setFilters(listeFilters);

        return jacksonValue;
    }*/

    //Produits/{id}
    @GetMapping(value = "Produits/{id}")
    public Product getProductById(@PathVariable int id){

       Product product = productDao.findById(id);
        if(product == null){
            throw new ProductNotFoundException("product with id "+id+" does not exist");
        }

        return product;
    }

    @PostMapping(value = "Produits")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody Product product, @PathVariable int id){
        
        Product p = productDao. save(product);

        if(p == null){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "Produits")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product){
        Product p = productDao.save(product);

        if(p == null){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

   @DeleteMapping(value = "Produits/{id}")
    public ResponseEntity<Void> deleteProduct(@RequestBody Product product){
        productDao.delete((product));

        return ResponseEntity.accepted().build();
    }
    @GetMapping(value = "Produits/recherche/{text}")
    public List<Product> searchProducts(@PathVariable String text){


        return productDao.findByNomLike("%"+text+"%");
    }

    @GetMapping(value = "Produits/list/{prixLimit}")
    public List<Product> getExpensiveProducts(@PathVariable int prixLimit){
        return productDao.chercherUnProduitCher(prixLimit);
    }

}
