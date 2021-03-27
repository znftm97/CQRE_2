package com.cqre.cqre.repository.category;

import com.cqre.cqre.entity.shop.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.identificationCode = :code")
    Category findCategoryByCode(@Param("code") String code);
}
