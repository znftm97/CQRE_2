package com.cqre.cqre.domain.shop.item;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopItem extends Item {

    private int armLength;
    private int bodyLength;
}
