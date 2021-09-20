package com.cqre.cqre.repository.gallery;

import com.cqre.cqre.domain.GalleryFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GalleryRepository extends JpaRepository<GalleryFile, Long>, GalleryRepositoryCustom{

    @Query(value = "select g from GalleryFile g join fetch g.user where g.bundleId = :bundleId", countQuery = "select count(g.id) from GalleryFile g where g.bundleId = :bundleId")
    Page<GalleryFile> findGalleryFileByBundleIdPaging(@Param("bundleId") Long bundleId, Pageable pageable);

    @Query("select g from GalleryFile g where g.bundleId = :bundleId")
    List<GalleryFile> findGalleryFileByBundleId(@Param("bundleId") Long bundleId);

    @Query("delete from GalleryFile g where g.id in :ids")
    @Modifying(clearAutomatically = true)
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
