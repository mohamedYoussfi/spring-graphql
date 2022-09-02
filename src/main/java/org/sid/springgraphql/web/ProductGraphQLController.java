package org.sid.springgraphql.web;

import lombok.extern.slf4j.Slf4j;
import org.sid.springgraphql.entities.Category;
import org.sid.springgraphql.entities.Product;
import org.sid.springgraphql.repositories.CategoryRepository;
import org.sid.springgraphql.repositories.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class ProductGraphQLController {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductGraphQLController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @QueryMapping
   public List<Category> categories(){
        return  categoryRepository.findAll();
   }
    @QueryMapping
    public List<Product> products(){
        return  productRepository.findAll();
    }
    @QueryMapping
    public Optional<Product> productById(@Argument String id){
        return productRepository.findById(id);
    }

    @MutationMapping
    public Product addProduct(@Argument ProductInput productInput){
        Category category=categoryRepository.findById((long)productInput.categoryId()).orElseThrow();
        Product product=new Product();
        product.setId(UUID.randomUUID().toString());
        if(productInput.name()!=null) product.setName(productInput.name());
        if(productInput.price()!=null)product.setPrice(productInput.price());
        if(productInput.quantity()!=null)product.setQuantity(productInput.quantity());
        product.setCategory(category);
        return productRepository.save(product);
    }
    @MutationMapping
    public String deleteProduct(@Argument String id){
        productRepository.deleteById(id);
        return id;
    }
    @MutationMapping
    public Product updateProduct(@Argument String id,@Argument ProductInput product){
        Category category=categoryRepository.findById(product.categoryId()).orElseThrow();
        Product productToUpdate=productRepository.findById(id).orElseThrow();
        if(product.name()!=null) productToUpdate.setName(product.name());
        if(product.price()!=null) productToUpdate.setPrice(product.price());
        if(product.quantity()!=null)productToUpdate.setQuantity(product.quantity());
        productToUpdate.setCategory(category);
        return productRepository.save(productToUpdate);
    }

}
record ProductInput(String name, Double price, Integer quantity, Long categoryId){}