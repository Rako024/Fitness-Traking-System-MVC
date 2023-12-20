package com.fitnesstraking.repostories;

import com.fitnesstraking.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {
 public List<Trainer> findAll();
}
