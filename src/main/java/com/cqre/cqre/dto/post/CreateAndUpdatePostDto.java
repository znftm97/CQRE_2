package com.cqre.cqre.dto.post;

import com.cqre.cqre.domain.post.Board;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateAndUpdatePostDto {
    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String title;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String content;

    private Long id;
    private Board board;
    private List<PostFileDto> files;

    public CreateAndUpdatePostDto() {

    }

    public CreateAndUpdatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CreateAndUpdatePostDto(String title, String content, Long id, Board board, List<PostFileDto> files) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.board = board;
        this.files = files;
    }
}
