package com.cqre.cqre.dto.gallery;

import com.cqre.cqre.entity.GalleryFile;
import lombok.Data;

@Data
public class FindGalleryFileDetailDto {

    private String title;
    private String filename;
    private Long bundleId;
    private String authorCheck;

    public FindGalleryFileDetailDto(GalleryFile galleryFile, String authorCheck) {
        this.title = galleryFile.getTitle();
        this.filename = galleryFile.getFilename();
        this.bundleId = galleryFile.getBundleId();
        this.authorCheck = authorCheck;
    }
}
