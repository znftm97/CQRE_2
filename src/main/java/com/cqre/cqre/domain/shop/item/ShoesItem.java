package com.cqre.cqre.domain.shop.item;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoesItem extends Item {

    private int stiffHeight;
}
