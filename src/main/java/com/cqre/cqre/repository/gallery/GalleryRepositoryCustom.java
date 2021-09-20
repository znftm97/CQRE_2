package com.cqre.cqre.repository.gallery;

import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GalleryRepositoryCustom {

    Page<FindGalleryFileDto> findAllDistinctByBundleId(Pageable pageable);
}
