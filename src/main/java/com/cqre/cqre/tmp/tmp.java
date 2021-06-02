package com.cqre.cqre.tmp;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class tmp {

    @Id
    @GeneratedValue
    @Column(name = "tmp_id")
    private Long id;

    private int num;
}
