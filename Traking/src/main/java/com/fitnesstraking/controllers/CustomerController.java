package com.fitnesstraking.controllers;

import com.fitnesstraking.entities.Customer;
import com.fitnesstraking.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    public CustomerService customerService;

    public CustomerController(CustomerService customerService){this.customerService = customerService;}
    @GetMapping("/singin")
    public String getSingin(Model model){
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers",customers);
        return "customer-login";
    }
    @PostMapping("/in")
    public String login(@RequestParam Long customerId, @RequestParam String password, Model model){
        Customer customer = customerService.checkPassword(customerId,password);
        if(customer !=null){
            model.addAttribute("customer",customer);
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            String day = dayOfWeek.toString().toLowerCase();
            model.addAttribute("weekOfDay",day);
            model.addAttribute("dayCarb",customerService.carbsProgress(customerId,day));
            model.addAttribute("dayProtein",customerService.proteinProgress(customerId,day));


            return "customer-info";
        }else return "redirect:/customer/singin";
    }


    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/save")
    public String saveCustomer(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam int weight,
                               @RequestParam int height,
                               @RequestParam String password)
    {
            customerService.saveCustomer(firstName,lastName,email,weight,height,password);
            return "redirect:/customer/singin";
    }
    @GetMapping("/daily-food/{customerId}/{day}")
    public String getFood(@PathVariable long customerId, @PathVariable String day, Model model) {
        System.out.println(customerId);
        Customer customer = customerService.findCustomerById(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("day", day);
        return "customer-daily-food";
    }


    @PostMapping("/save-nutrition")
    public String saveFood(@RequestParam long customerId,@RequestParam String day, @RequestParam int carbohydrate, @RequestParam int protein){
        customerService.saveNutrition(customerId,day,carbohydrate,protein);
        return "redirect:/customer/singin";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable long customerId){
        customerService.deleteCustomerById(customerId);
        return "redirect:/customer/singin";
    }
}
