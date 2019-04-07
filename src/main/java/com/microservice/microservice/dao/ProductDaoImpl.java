package com.microservice.microservice.dao;

import com.microservice.microservice.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl {

}
/*
@Repository
public class ProductDaoImpl implements ProductDao {

    static List<Product> products = new ArrayList<Product>();

    static {
        products.add(new Product(1, "Ordinateur", 200,120));
        products.add(new Product(2, "Machine", 122, 100));
        products.add(new Product(3, "Laptop", 400, 300));
    };
    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        for (Product product : products) {
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }
}
*/