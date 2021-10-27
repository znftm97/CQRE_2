package com.cqre.cqre.repository.post;

import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.PostSearchCondition;
import com.cqre.cqre.dto.post.QListPostDto;
import com.cqre.cqre.domain.board.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cqre.cqre.domain.board.QPost.*;
import static com.cqre.cqre.domain.user.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ListPostDto> searchPost(PostSearchCondition condition, Pageable pageable) {
        QueryResults<ListPostDto> results = jpaQueryFactory
                .select(new QListPostDto(
                        post.id,
                        post.title,
                        post.content,
                        post.postViews,
                        post.recommendation,
                        post.createDate,
                        user.name))
                .from(post)
                .leftJoin(post.user, user)
                .where(
                        usernameEq(condition.getName()),
                        titleEq(condition.getTitle()),
                        boardEq(condition.getBoard())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ListPostDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameEq(String usernameParam){
        return usernameParam != null ? user.name.eq(usernameParam) : null;
    }

    private BooleanExpression titleEq(String titleParam){
        return titleParam != null ? post.title.eq(titleParam) : null;
    }
    private BooleanExpression boardEq(Board boardParam){
        return boardParam != null ? post.board.eq(boardParam) : null;
    }

}
