package com.fitnesstraking.controllers;

import com.fitnesstraking.entities.Customer;
import com.fitnesstraking.entities.Trainer;
import com.fitnesstraking.services.CustomerService;
import com.fitnesstraking.services.MenegerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MenegerController {
    private final MenegerService menegerService;

    public MenegerController(MenegerService menegerService) {
        this.menegerService = menegerService;
    }

    @GetMapping("/c2t")
    public String getC2T(Model model){
        List<Trainer> trainers = menegerService.getAllTrainers();
        List<Customer> customers = menegerService.getCustomerAllWithoutTrainer();
        model.addAttribute("trainers",trainers);
        model.addAttribute("customers",customers);
        return "manager-customertotrainer";
    }

    @PostMapping("/caddt")
    public String postC2T(@RequestParam Long trainerId, @RequestParam Long customerId){
        menegerService.addCustomerToTrainer(trainerId,customerId);
        return "redirect:/admin/c2t";
    }

    @GetMapping("/")
    public String showTrainerList(Model model) {
        List<Trainer> trainers = menegerService.getAllTrainers();

        List<Customer> customers = menegerService.getCustomerAllWithoutTrainer();
        model.addAttribute("trainers", trainers);
        model.addAttribute("customers",customers);
        return "manager-main";
    }

    @GetMapping("/trainer-register")
    public String getRegister(){
        return "trainer-register";
    }

    @PostMapping("/trainer-save")
    public String saveTrainer(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam String password){
        menegerService.saveTrainer(firstName,lastName,email,password);
        return "redirect:/admin/";
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@RequestParam Long customerId) {
        menegerService.deleteCustomer(customerId);
        return "redirect:/admin/";
    }

    @PostMapping("/dissociate-customer")
    public String dissociateCustomerFromTrainer(@RequestParam Long customerId) {
        menegerService.dissociateCustomerFromTrainer(customerId);
        return "redirect:/admin/";
    }

    @PostMapping("/delete-trainer")
    public String deleteTrainer(@RequestParam Long trainerId) {
        menegerService.deleteTrainer(trainerId);
        return "redirect:/admin/";
    }

    @GetMapping("/edit-trainer/{trainerId}")
    public String getEditTrainerPage(@PathVariable Long trainerId, Model model) {
        Trainer trainer = menegerService.getTrainerById(trainerId);
        model.addAttribute("trainer", trainer);
        return "manager-edittrainer";
    }

    @PostMapping("/edit-trainer")
    public String editTrainer(@RequestParam Long trainerId,
                              @RequestParam String firstName,
                              @RequestParam String lastName) {
        menegerService.editTrainer(trainerId, firstName, lastName);
        return "redirect:/admin/";
    }
}
