package com.es.digitalwallet.domain.entity;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class Customer extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "tckn", nullable = false)
    private String tckn;

    public static Customer of(String name, String surname, String tckn) {
        Customer customer = new Customer();
        customer.name = name;
        customer.surname = surname;
        customer.tckn = tckn;
        return customer;
    }
}
