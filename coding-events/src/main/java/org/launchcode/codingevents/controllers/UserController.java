package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.UserRepository;
import org.launchcode.codingevents.models.User;
import org.launchcode.codingevents.models.UserCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Random;

@Controller
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private static final Random RANDOM = new SecureRandom();

    @GetMapping
    public String displayAllUsers(Model model) {
        model.addAttribute("title", "All Users");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userCategories", UserCategory.values());
        return "users/index";
    }

    @GetMapping("create")
    public String displayRegisterForm(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());
        model.addAttribute("userCategories", UserCategory.values());
        return "users/create";
    }

    @PostMapping("create")
    public String processRegisterForm(@ModelAttribute User user, Model model) {

        model.addAttribute("title", "Register");
        Iterable<User> userList = userRepository.findAll();
        model.addAttribute("title", "Create User");
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty()){
            model.addAttribute("errorMsg", "Invalid fields.");
            return "users/create";
        }
        else{
            for (User x : userList){
                if (x.getUserName().equals(user.getUserName())){
                    model.addAttribute("errorMsg", "User already exists!");
                    return "users/create";
                }
            }
        }
        user.setPassword(String.valueOf(user.hashCode()) + getSalt());
        userRepository.save(user);
        return "redirect:";
    }


    @GetMapping("delete")
    public String displayDeleteUserForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("users", userRepository.findAll());
        return "users/delete";
    }

    @PostMapping("delete")
    public String processDeleteUserForm(@RequestParam(required = false) int[] userIds) {

        if(userIds!=null) {
            for (int id : userIds) {
                userRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("update")
    public String displayUpdateUserForm(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());
        model.addAttribute("userCategories", UserCategory.values());
        return "users/update";
    }

    @PostMapping("update")
    public String processUpdateUserForm(@ModelAttribute User user, Model model) {

        model.addAttribute("title", "Update");
        Iterable<User> userList = userRepository.findAll();
        model.addAttribute("title", "Update User");
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            model.addAttribute("errorMsg", "Invalid fields.");
            return "users/update";
        } else {
            for (User x : userList) {
                if (x.getUserName().equals(user.getUserName())) {
                    x.setPassword(String.valueOf(user.hashCode()) + getSalt());
                    x.setUserCategory(user.getUserCategory());
                    userRepository.save(x);
                }
            }
        }
        return "redirect:";
    }

    private static byte[] getSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

}
