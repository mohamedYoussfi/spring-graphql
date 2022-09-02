package org.sid.springgraphql.repositories;

import org.sid.springgraphql.entities.Category;
import org.sid.springgraphql.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
