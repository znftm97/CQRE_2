package com.cqre.cqre.entity.shop.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyNecessity extends Item{

    private String manufacturer;
    private String lifeSpan;

    @Builder
    public DailyNecessity(String manufacturer, String lifeSpan){
        this.manufacturer = manufacturer;
        this.lifeSpan = lifeSpan;
    }
}
