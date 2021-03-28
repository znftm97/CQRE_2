package com.cqre.cqre.service;

import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.dto.item.FindItemDto;
import com.cqre.cqre.entity.shop.item.Category;
import com.cqre.cqre.entity.shop.item.CommonItem;
import com.cqre.cqre.repository.Item.ItemRepository;
import com.cqre.cqre.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public Page<FindItemDto> findItems(Pageable pageable){
        Page<FindItemDto> itemsWithImagesDistinct = itemRepository.findItemsWithImagesDistinct(pageable);
        return itemsWithImagesDistinct;
    }
}
