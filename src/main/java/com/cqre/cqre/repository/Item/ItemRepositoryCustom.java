package com.cqre.cqre.repository.Item;

import com.cqre.cqre.dto.item.FindItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<FindItemDto> findItemsWithImagesDistinct(Pageable pageable);
}
