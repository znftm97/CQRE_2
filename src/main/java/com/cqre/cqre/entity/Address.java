package com.cqre.cqre.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String street = "Not yet entered";
    private String detail = "Not yet entered";

    protected Address(){

    }

    public Address(String street, String detail) {
        this.street = street;
        this.detail = detail;
    }
}
