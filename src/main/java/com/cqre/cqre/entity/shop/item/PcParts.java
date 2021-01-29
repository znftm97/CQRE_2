package com.cqre.cqre.entity.shop.item;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PcParts extends Item{

    private String usage;
    private String size;
}
