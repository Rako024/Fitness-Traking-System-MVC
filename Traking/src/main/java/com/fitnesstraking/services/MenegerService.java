package com.fitnesstraking.services;

import com.fitnesstraking.entities.Customer;
import com.fitnesstraking.entities.Trainer;
import com.fitnesstraking.repostories.CustomerRepository;
import com.fitnesstraking.repostories.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MenegerService {
    private final CustomerRepository customerRepository;
    private final TrainerRepository trainerRepository;

    public MenegerService(CustomerRepository customerRepository, TrainerRepository trainerRepository){
        this.customerRepository = customerRepository;
        this.trainerRepository = trainerRepository;
    }

    public void addCustomerToTrainer(Long trainerId, Long customerId){
        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerId);
        Trainer trainer = trainerOptional.get();

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Customer customer = customerOptional.get();
        trainer.getCustomers().add(customer);
        customer.setTrainer(trainer);
        trainerRepository.save(trainer);
        customerRepository.save(customer);
    }
    public List<Customer> getCustomersByTrainerId(Long trainerId) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerId);

        if (trainerOptional.isPresent()) {
            Trainer trainer = trainerOptional.get();
            return trainer.getCustomers();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Trainer> getAllTrainers(){
        return trainerRepository.findAll();
    }

    public boolean saveTrainer(String firstname,
                              String lastname,
                              String email,
                              String password){
        try {
            Trainer trainer = new Trainer();
            trainer.setName(firstname);
            trainer.setSurname(lastname);
            trainer.setMail(email);
            trainer.setPassword(password);
            trainerRepository.save(trainer);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    public List<Customer> getCustomerAllWithoutTrainer(){
        return customerRepository.findByTrainerIsNull();
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void dissociateCustomerFromTrainer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setTrainer(null);
            customerRepository.save(customer);
        }
    }
    public void deleteTrainer(Long trainerId) {
        trainerRepository.deleteById(trainerId);
    }

    public void editTrainer(Long trainerId, String firstName, String lastName) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerId);
        trainerOptional.ifPresent(trainer -> {
            trainer.setName(firstName);
            trainer.setSurname(lastName);
            trainerRepository.save(trainer);
        });
    }

    public Trainer getTrainerById(Long id){
        Optional<Trainer> trainer =trainerRepository.findById(id);
        return trainer.get();
    }
}
