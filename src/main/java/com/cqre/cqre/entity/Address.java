package com.cqre.cqre.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String street;
    private String detail;

    protected Address(){

    }

    public Address(String street, String detail) {
        this.street = street;
        this.detail = detail;
    }
}
