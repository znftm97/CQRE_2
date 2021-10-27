package com.cqre.cqre.repository.gallery;

import com.cqre.cqre.domain.user.QUser;
import com.cqre.cqre.dto.gallery.FindGalleryFileDistinctDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import com.cqre.cqre.dto.gallery.QFindGalleryFileDistinctDto;
import com.cqre.cqre.dto.gallery.QFindGalleryFileDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.cqre.cqre.domain.QGalleryFile.galleryFile;

@RequiredArgsConstructor
public class GalleryRepositoryImpl implements GalleryRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<FindGalleryFileDto> findAllDistinctByBundleId(Pageable pageable) {
        List<FindGalleryFileDistinctDto> fetch = jpaQueryFactory
                .select(new QFindGalleryFileDistinctDto(
                        galleryFile.bundleId,
                        galleryFile.id.min()
                ))
                .from(galleryFile)
                .groupBy(galleryFile.bundleId)
                .fetch();

        List<Long> collect = fetch.stream()
                .map(f -> f.getId())
                .collect(Collectors.toList());

        QueryResults<FindGalleryFileDto> result = jpaQueryFactory
                .select(new QFindGalleryFileDto(
                        galleryFile.id,
                        galleryFile.title,
                        galleryFile.filePath,
                        galleryFile.createDate,
                        QUser.user.name,
                        galleryFile.bundleId,
                        galleryFile.bundleOrder))
                .from(galleryFile)
                .where(galleryFile.id.in(collect))
                .leftJoin(galleryFile.user, QUser.user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(galleryFile.bundleId.desc())
                .fetchResults();

        List<FindGalleryFileDto> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
