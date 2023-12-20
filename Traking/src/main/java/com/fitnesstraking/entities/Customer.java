package com.fitnesstraking.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    String surname;
    String mail;
    int weight;
    int height;
    String password;
    @Column(length = 1000)
    String weekly_food_plan;

    int monday_carb;
    int tuesday_carb;
    int wednesday_carb;
    int thursday_carb;
    int friday_carb;
    int saturday_carb;
    int sunday_carb;
    int trainer_monday_carb;
    int trainer_tuesday_carb;
    int trainer_wednesday_carb;
    int trainer_thursday_carb;
    int trainer_friday_carb;
    int trainer_saturday_carb;
    int trainer_sunday_carb;

    int monday_protein;
    int tuesday_protein;
    int wednesday_protein;
    int thursday_protein;
    int friday_protein;
    int saturday_protein;
    int sunday_protein;
    int trainer_monday_protein;
    int trainer_tuesday_protein;
    int trainer_wednesday_protein;
    int trainer_thursday_protein;
    int trainer_friday_protein;
    int trainer_saturday_protein;
    int trainer_sunday_protein;
    @Column(length = 1000)
    String weekly_activity_plan;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;




}
