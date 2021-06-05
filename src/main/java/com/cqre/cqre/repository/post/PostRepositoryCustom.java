package com.cqre.cqre.repository.post;

import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.PostSearchCondition;
import com.cqre.cqre.dto.post.ReadPostWithDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<ListPostDto> searchPost(PostSearchCondition condition, Pageable pageable);
}
