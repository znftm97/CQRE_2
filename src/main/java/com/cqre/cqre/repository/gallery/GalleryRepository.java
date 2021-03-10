package com.cqre.cqre.repository.gallery;

import com.cqre.cqre.entity.GalleryFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<GalleryFile, Long>, GalleryRepositoryCustom{

}
