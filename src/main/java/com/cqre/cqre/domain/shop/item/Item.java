package com.cqre.cqre.domain.shop.item;

import com.cqre.cqre.domain.BaseEntity;
import com.cqre.cqre.exception.customexception.item.CNotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private ItemGender gender;

    private String itemExplanation;

    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void removeStock(int stock) {
        int restStock = this.stock - stock;
        if( restStock < 0){
            throw new CNotEnoughStockException();
        }
        this.stock = restStock;
    }

    public void addStock(int stock) {
        this.stock += stock;
    }
}
