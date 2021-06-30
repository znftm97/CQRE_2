package com.cqre.cqre.repository.Item;

import com.cqre.cqre.dto.item.FindItemDto;
import com.cqre.cqre.dto.item.FindItemImageDistinctDto;
import com.cqre.cqre.dto.item.QFindItemDto;
import com.cqre.cqre.dto.item.QFindItemImageDistinctDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.cqre.cqre.entity.shop.QItemImage.itemImage;
import static com.cqre.cqre.entity.shop.item.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindItemDto> findItemsWithImagesDistinct(Pageable pageable) {
        List<FindItemImageDistinctDto> fetch = queryFactory
                .select(new QFindItemImageDistinctDto(
                        itemImage.bundleId,
                        itemImage.id.min()
                ))
                .from(itemImage)
                .groupBy(itemImage.bundleId)
                .fetch();

        List<Long> collect = fetch.stream()
                .map(i -> i.getId())
                .collect(Collectors.toList());

        QueryResults<FindItemDto> result = queryFactory
                .select(new QFindItemDto(
                        item.id,
                        item.name,
                        item.itemExplanation,
                        item.price,
                        itemImage.filePath,
                        itemImage.bundleId
                        ))
                .from(itemImage)
                .where(itemImage.id.in(collect))
                .leftJoin(itemImage.item, item)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(itemImage.bundleId.desc())
                .fetchResults();

        List<FindItemDto> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<FindItemDto> findItemsWithImagesByCategory(Pageable pageable, String categoryName) {
        List<FindItemImageDistinctDto> fetch = queryFactory
                .select(new QFindItemImageDistinctDto(
                        itemImage.bundleId,
                        itemImage.id.min()
                ))
                .from(itemImage)
                .groupBy(itemImage.bundleId)
                .fetch();

        List<Long> collect = fetch.stream()
                .map(i -> i.getId())
                .collect(Collectors.toList());

        QueryResults<FindItemDto> result = queryFactory
                .select(new QFindItemDto(
                        item.id,
                        item.name,
                        item.itemExplanation,
                        item.price,
                        itemImage.filePath,
                        itemImage.bundleId
                        ))
                .from(itemImage)
                .where(itemImage.id.in(collect))
                .where(item.category.name.eq(categoryName))
                .leftJoin(itemImage.item, item)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<FindItemDto> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
