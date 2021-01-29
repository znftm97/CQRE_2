package com.cqre.cqre.entity.shop.item;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyNecessity extends Item{

    private String manufacturer;
    private String lifeSpan;
}
