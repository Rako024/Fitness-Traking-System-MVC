package com.fitnesstraking.services;

import com.fitnesstraking.entities.Customer;
import com.fitnesstraking.entities.Trainer;
import com.fitnesstraking.repostories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    public Customer customer;
    public CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public void saveCustomer(String firstname, String lastname, String email, int weight, int height, String password){
        Customer customer = new Customer();
        customer.setName(firstname);
        customer.setSurname(lastname);
        customer.setMail(email);
        customer.setWeight(weight);
        customer.setHeight(height);
        customer.setPassword(password);
        customerRepository.save(customer);
    }

    public List<Customer> getCustomersWithoutTrainer() {
        return customerRepository.findByTrainerIsNull();
    }

    public Customer findCustomerById(Long Id){
        Optional<Customer> customerOptional = customerRepository.findById(Id);
        return customerOptional.get();
    }
    public List<Customer> findAll(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }
    public double carbsProgress(long customerId, String dayOfWeek) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            switch (dayOfWeek.toLowerCase()) {
                case "monday":
                    return ((double) customer.getMonday_carb() / customer.getTrainer_monday_carb()) * 100;
                case "tuesday":
                    return ((double) customer.getTuesday_carb() / customer.getTrainer_tuesday_carb()) * 100;
                case "wednesday":
                    return ((double) customer.getWednesday_carb() / customer.getTrainer_wednesday_carb()) * 100;
                case "thursday":
                    return ((double) customer.getThursday_carb() / customer.getTrainer_thursday_carb()) * 100;
                case "friday":
                    return ((double) customer.getFriday_carb() / customer.getTrainer_friday_carb()) * 100;
                case "saturday":
                    return ((double) customer.getSaturday_carb() / customer.getTrainer_saturday_carb()) * 100;
                case "sunday":
                    return ((double) customer.getSunday_carb() / customer.getTrainer_sunday_carb()) * 100;
            }
        }

        return 0;
    }
    public double proteinProgress(long customerId, String dayOfWeek) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            switch (dayOfWeek.toLowerCase()) {
                case "monday":
                    return ((double) customer.getMonday_protein() / customer.getTrainer_monday_protein()) * 100;
                case "tuesday":
                    return ((double) customer.getTuesday_protein() / customer.getTrainer_tuesday_protein()) * 100;
                case "wednesday":
                    return ((double) customer.getWednesday_protein() / customer.getTrainer_wednesday_protein()) * 100;
                case "thursday":
                    return ((double) customer.getThursday_protein() / customer.getTrainer_thursday_protein()) * 100;
                case "friday":
                    return ((double) customer.getFriday_protein() / customer.getTrainer_friday_protein()) * 100;
                case "saturday":
                    return ((double) customer.getSaturday_protein() / customer.getTrainer_saturday_protein()) * 100;
                case "sunday":
                    return ((double) customer.getSunday_protein() / customer.getTrainer_sunday_protein()) * 100;
            }
        }

        return 0;
    }

    public Customer checkPassword(Long id, String password){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = customerOptional.get();
        if (customer.getPassword().equals(password)){
            return customer;
        }else
            return null;
    }
    public void saveNutrition(long customerId, String day, int carbohydrate, int protein) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            // Gün adına göre ilgili günün değerini güncelle
            switch (day.toLowerCase()) {
                case "monday":
                    customer.setMonday_carb(carbohydrate);
                    customer.setMonday_protein(protein);
                    break;
                case "tuesday":
                    customer.setTuesday_carb(carbohydrate);
                    customer.setTuesday_protein(protein);
                    break;
                case "wednesday":
                    customer.setWednesday_carb(carbohydrate);
                    customer.setWednesday_protein(protein);
                    break;
                case "thursday":
                    customer.setThursday_carb(carbohydrate);
                    customer.setThursday_protein(protein);
                    break;
                case "friday":
                    customer.setFriday_carb(carbohydrate);
                    customer.setFriday_protein(protein);
                    break;
                case "saturday":
                    customer.setSaturday_carb(carbohydrate);
                    customer.setSaturday_protein(protein);
                    break;
                case "sunday":
                    customer.setSunday_carb(carbohydrate);
                    customer.setSunday_protein(protein);
                    break;
                default:

                    throw new IllegalArgumentException("Invalid day: " + day);
            }
            customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
    }

    public void deleteCustomerById(long customerId) {
        customerRepository.deleteById(customerId);
    }
}
