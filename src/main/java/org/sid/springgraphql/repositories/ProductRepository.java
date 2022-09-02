package org.sid.springgraphql.repositories;

import org.sid.springgraphql.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
