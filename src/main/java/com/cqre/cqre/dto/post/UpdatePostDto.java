package com.cqre.cqre.dto.post;

import com.cqre.cqre.entity.post.Board;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePostDto {
    private Long id;
    private String title;
    private String content;
    private Board board;
    private List<PostFileDto> files;

    public UpdatePostDto(Long id, String title, String content, Board board, List<PostFileDto> files) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.board = board;
        this.files = files;
    }
}
