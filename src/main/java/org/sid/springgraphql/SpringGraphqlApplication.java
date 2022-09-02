package org.sid.springgraphql;

import org.sid.springgraphql.entities.Category;
import org.sid.springgraphql.entities.Product;
import org.sid.springgraphql.repositories.CategoryRepository;
import org.sid.springgraphql.repositories.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGraphqlApplication.class, args);
    }
}
@Component
class AppRunner implements ApplicationRunner {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    AppRunner(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("Computer","Printer","Smartphone").forEach(name->{
            categoryRepository.save(new Category(null, name,null));
        });
        categoryRepository.findAll().forEach(category -> {
            for (int i = 0; i <4 ; i++) {
                productRepository.save(
                        new Product(
                                UUID.randomUUID().toString(),
                                category.getName()+" "+i,
                                Math.random()*9000,
                                new Random().nextInt(8000),
                                category )
                );
            }
        });
        productRepository.findAll().forEach(p->{
            System.out.println(p.getName());
        });
    }
}
