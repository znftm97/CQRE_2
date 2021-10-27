package com.cqre.cqre.repository;

import com.cqre.cqre.domain.shop.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.identificationCode = :code")
    Category findCategoryByCode(@Param("code") String code);

    @Query("select c from Category c")
    List<Category> findCategoryAll();
}
