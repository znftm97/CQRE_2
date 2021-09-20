package com.cqre.cqre.dto.post;

import com.cqre.cqre.domain.post.Board;
import lombok.Data;

@Data
public class PostSearchCondition {

    private String name;
    private String title;
    private String searchWord;
    private String searchSelect;
    private Board board;
}
