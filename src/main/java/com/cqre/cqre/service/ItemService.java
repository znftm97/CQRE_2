package com.cqre.cqre.service;

import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.dto.item.FindItemDetailDto;
import com.cqre.cqre.dto.item.FindItemDto;
import com.cqre.cqre.entity.shop.item.Category;
import com.cqre.cqre.entity.shop.item.CommonItem;
import com.cqre.cqre.entity.shop.item.Item;
import com.cqre.cqre.repository.Item.ItemRepository;
import com.cqre.cqre.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;

    /*상품 생성*/
    @Transactional
    public void createItem(CreateItemDto dto, List<MultipartFile> files, String dirName) throws IOException {
        Category findCategory = categoryRepository.findCategoryByCode(dto.getCategorySelect());
        CommonItem commonItem = new CommonItem();
        CommonItem createCommonItem = commonItem.createCommonItem(dto, findCategory);

        itemRepository.save(createCommonItem);
        /*itemImageService.createItemImage(files, createCommonItem);*/
        itemImageService.upload1(files, dirName, createCommonItem);
    }

    /*모든 상품 조회*/
    public Page<FindItemDto> findItems(Pageable pageable){
        return itemRepository.findItemsWithImagesDistinct(pageable);
    }

    /*상품 상세 조회*/
    public FindItemDetailDto findItemDetail(Long itemId) {
        Item findItem = itemRepository.findItemsWithCategory(itemId);
        return new FindItemDetailDto(findItem);
    }

    /*카테고리별 상품 조회*/
    public Page<FindItemDto> findItemsByCategory(Pageable pageable, String categoryName) {
        return itemRepository.findItemsWithImagesByCategory(pageable, categoryName);
    }
}
