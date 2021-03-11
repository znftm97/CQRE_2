package com.cqre.cqre.repository.gallery;

import com.cqre.cqre.entity.GalleryFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GalleryRepository extends JpaRepository<GalleryFile, Long>, GalleryRepositoryCustom{

    @Query("select g from GalleryFile g where g.bundleId = :bundleId")
    Page<GalleryFile> findGalleryFileByBundleId(@Param("bundleId") Long bundleId, Pageable pageable);
}
