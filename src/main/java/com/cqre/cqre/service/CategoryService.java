package com.cqre.cqre.service;

import com.cqre.cqre.domain.shop.item.Category;
import com.cqre.cqre.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<String> findCategoryNames() {
        List<Category> findCategorys = categoryRepository.findCategoryAll();
        return findCategorys.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());

    }
}
