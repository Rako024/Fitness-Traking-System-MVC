package com.fitnesstraking.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Meneger {
    @Id
    long id;
    String name;
    String password;
}
