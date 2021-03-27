package com.cqre.cqre.service;

import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.entity.shop.item.Category;
import com.cqre.cqre.entity.shop.item.CommonItem;
import com.cqre.cqre.repository.Item.ItemRepository;
import com.cqre.cqre.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;

    @Transactional
    public void createItem(CreateItemDto dto, MultipartFile[] files) {
        Category findCategory = categoryRepository.findCategoryByCode(dto.getCategorySelect());
        CommonItem commonItem = new CommonItem();
        CommonItem createCommonItem = commonItem.createCommonItem(dto, findCategory);

        itemRepository.save(createCommonItem);
        itemImageService.createItemImage(files, createCommonItem);
    }
}
