package com.cqre.cqre.entity.shop.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PcParts extends Item{

    private String usage;
    private String size;

    @Builder
    public PcParts(String usage, String size){
        this.usage = usage;
        this.size = size;
    }
}
