package com.cqre.cqre.entity.shop.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{

    private String author;
    private String publisher;

    @Builder
    public Book(String author, String publisher){
        this.author = author;
        this.publisher = publisher;
    }
}
