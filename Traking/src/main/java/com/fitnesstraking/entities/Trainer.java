package com.fitnesstraking.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    String surname;
    String mail;
    String password;


    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Customer> customers;

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }


}
