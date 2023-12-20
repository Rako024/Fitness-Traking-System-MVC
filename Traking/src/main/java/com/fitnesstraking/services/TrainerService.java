package com.fitnesstraking.services;

import com.fitnesstraking.entities.Customer;
import com.fitnesstraking.entities.Trainer;
import com.fitnesstraking.repostories.CustomerRepository;
import com.fitnesstraking.repostories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {
    TrainerRepository trainerRepository;
    CustomerRepository customerRepository;

    public TrainerService(TrainerRepository trainerRepository, CustomerRepository customerRepository){this.trainerRepository = trainerRepository;
    this.customerRepository =customerRepository;
    }
    public Trainer checkPassword(Long id, String password){
        Optional<Trainer> trainerOptional = trainerRepository.findById(id);
        Trainer trainer = trainerOptional.get();
        if (trainer.getPassword().equals(password)){
            return trainer;
        }else
            return null;
    }

    public List<Trainer> findAllTrainer(){
        return trainerRepository.findAll();
    }

    public void updateCustomerFoodPlan(Long customerId, String weeklyRation) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        customerOptional.ifPresent(customer -> {
            customer.setWeekly_food_plan(weeklyRation);
            customerRepository.save(customer);
        });
    }
    public void updateCustomerDailyNutrition(Long customerId, String dayOfWeek, int carbs, int protein) {
        // Customer'ın günlük beslenme bilgilerini güncelleme işlemleri
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            switch (dayOfWeek.toLowerCase()) {
                case "monday":
                    customer.setTrainer_monday_carb(carbs);
                    customer.setTrainer_monday_protein(protein);
                    break;
                case "tuesday":
                    customer.setTrainer_tuesday_carb(carbs);
                    customer.setTrainer_tuesday_protein(protein);
                    break;
                case "wednesday":
                    customer.setTrainer_wednesday_carb(carbs);
                    customer.setTrainer_wednesday_protein(protein);
                    break;
                case "thursday":
                    customer.setTrainer_thursday_carb(carbs);
                    customer.setTrainer_thursday_protein(protein);
                    break;
                case "friday":
                    customer.setTrainer_friday_carb(carbs);
                    customer.setTrainer_friday_protein(protein);
                    break;
                case "saturday":
                    customer.setTrainer_saturday_carb(carbs);
                    customer.setTrainer_saturday_protein(protein);
                    break;
                case "sunday":
                    customer.setTrainer_sunday_carb(carbs);
                    customer.setTrainer_sunday_protein(protein);
                    break;
            }
            customerRepository.save(customer);
        }
    }

    public void updateCustomerActivityPlan(Long customerId, String weeklyActivity) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        customerOptional.ifPresent(customer -> {
            customer.setWeekly_activity_plan(weeklyActivity);
            customerRepository.save(customer);
        });
    }
}
