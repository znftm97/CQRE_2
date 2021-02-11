package com.cqre.cqre.dto.post;

import com.cqre.cqre.dto.user.UserDto;
import com.cqre.cqre.entity.post.Post;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private int postViews;
    private int recommendation;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private UserDto userDto;

    public PostDto(){
    }

    public PostDto(Post post){
        ModelMapper modelMapper = new ModelMapper();

        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        postViews = post.getPostViews();
        recommendation = post.getRecommendation();
        createDate = post.getCreateDate();
        userDto = modelMapper.map(post.getUser(), UserDto.class);
    }
}
