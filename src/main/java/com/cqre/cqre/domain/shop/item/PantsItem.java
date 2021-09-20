package com.cqre.cqre.domain.shop.item;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PantsItem extends Item {

    private int length;
    private int circumferenceLength;
}
