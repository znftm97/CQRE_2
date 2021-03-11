package com.cqre.cqre.dto.gallery;

import com.cqre.cqre.entity.GalleryFile;
import lombok.Data;

@Data
public class FindGalleryFileDetailDto {

    private String title;
    private String filename;
    private Long bundleId;

    public FindGalleryFileDetailDto(GalleryFile galleryFile) {
        this.title = galleryFile.getTitle();
        this.filename = galleryFile.getFilename();
        this.bundleId = galleryFile.getBundleId();
    }
}
