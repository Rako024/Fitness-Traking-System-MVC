package com.fitnesstraking.controllers;

import com.fitnesstraking.entities.Customer;
import com.fitnesstraking.entities.Trainer;
import com.fitnesstraking.services.CustomerService;
import com.fitnesstraking.services.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    public TrainerService trainerService;
    public CustomerService customerService;

    public TrainerController(TrainerService trainerService, CustomerService customerService){this.trainerService = trainerService;
    this.customerService = customerService;
    }

    @GetMapping("/")
    public String showMainPage(){
        return "trainer-main";
    }
    @GetMapping("/singin")
    public String getLogin(Model model){
        List<Trainer> trainers =  trainerService.findAllTrainer();
        model.addAttribute("trainers",trainers);

        return "trainer-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam Long trainerId, @RequestParam String password, Model model){
        Trainer trainer = trainerService.checkPassword(trainerId,password);
        if(trainer !=null){
            model.addAttribute("trainer",trainer);
            model.addAttribute("customers",trainer.getCustomers());
            return "trainer-main";
        }else return "redirect:/trainer/login";
    }

    @GetMapping("/food/{customerId}")
    public String weeklyFood(@PathVariable Long customerId, Model model){
        Customer customer = customerService.findCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "trainer-food";
    }
    @PostMapping("/edit-food")
    public String editWeeklyFood(@RequestParam Long customerId,
                                 @RequestParam String weeklyRation,
                                 @RequestParam int mondayCarbs, @RequestParam int mondayProtein,
                                 @RequestParam int tuesdayCarbs, @RequestParam int tuesdayProtein,
                                 @RequestParam int wednesdayCarbs, @RequestParam int wednesdayProtein,
                                 @RequestParam int thursdayCarbs, @RequestParam int thursdayProtein,
                                 @RequestParam int fridayCarbs, @RequestParam int fridayProtein,
                                 @RequestParam int saturdayCarbs, @RequestParam int saturdayProtein,
                                 @RequestParam int sundayCarbs, @RequestParam int sundayProtein) {
        trainerService.updateCustomerFoodPlan(customerId, weeklyRation);

        // Aşağıda, her gün için karbonhidrat ve protein değerlerini kaydetme işlemleri yapılır
        trainerService.updateCustomerDailyNutrition(customerId, "Monday", mondayCarbs, mondayProtein);
        trainerService.updateCustomerDailyNutrition(customerId, "Tuesday", tuesdayCarbs, tuesdayProtein);
        trainerService.updateCustomerDailyNutrition(customerId, "Wednesday", wednesdayCarbs, wednesdayProtein);
        trainerService.updateCustomerDailyNutrition(customerId, "Thursday", thursdayCarbs, thursdayProtein);
        trainerService.updateCustomerDailyNutrition(customerId, "Friday", fridayCarbs, fridayProtein);
        trainerService.updateCustomerDailyNutrition(customerId, "Saturday", saturdayCarbs, saturdayProtein);
        trainerService.updateCustomerDailyNutrition(customerId, "Sunday", sundayCarbs, sundayProtein);

        return "redirect:/trainer/singin";
    }

    @GetMapping("/activity/{customerId}")
    public String weeklyActivity(@PathVariable Long customerId, Model model){
        Customer customer = customerService.findCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "trainer-activity";
    }
    @PostMapping("/edit-activity")
    public String editWeeklyactivity(@RequestParam Long customerId,@RequestParam String weeklyRation){
        trainerService.updateCustomerActivityPlan(customerId,weeklyRation);
        Customer customer = customerService.findCustomerById(customerId);
        Trainer trainer = customer.getTrainer();
        return "redirect:/trainer/singin";
    }

    @GetMapping("/customer-info/{customerId}")
    public String getCustomerİnfo(@PathVariable Long customerId, Model model){
        Customer customer = customerService.findCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "trainer-customerinfo";
    }
}
