package com.cqre.cqre.dto.gallery;

import com.cqre.cqre.domain.GalleryFile;
import lombok.Data;

@Data
public class FindGalleryFileDetailDto {

    private String title;
    private String filePath;
    private Long bundleId;
    private String authorCheck;

    public FindGalleryFileDetailDto(GalleryFile galleryFile, String authorCheck) {
        this.title = galleryFile.getTitle();
        this.filePath = galleryFile.getFilePath();
        this.bundleId = galleryFile.getBundleId();
        this.authorCheck = authorCheck;
    }
}
