package org.es.auth.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@Table(name = "customer")
@Entity
public class Customer extends BaseEntity {
    private String name;
    private String surname;
    private String tckn;
    private String username;
    private String password;
    private String email;
    private Date birthDate;
    private String phone;
}

