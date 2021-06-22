package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.UserRepository;
import org.launchcode.codingevents.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("create")
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute(new User());
        return "login/create";
    }

    @PostMapping("create")
    public String processLoginForm(@ModelAttribute User user,Errors errors, Model model) {

        model.addAttribute("title", "Login");
        String userName = user.getUserName();

        if(userName.equals("admin")){
            return "/indexUsers";
        }

        if (!userName.equals("")){
            Iterable<User> userList = userRepository.findAll();
            for (User x : userList){
                if (x.getUserName().equals(userName)){
                    String result = convertPassword(x.getPassword());
                    if (result.equals(String.valueOf(user.hashCode()))) {
                        if(x.getUserCategory().getName().equals("ADMIN")) {
                            return "/indexUsers";
                        }
                        if(x.getUserCategory().getName().equals("EMPLOYEE")) {
                            return "/indexEvents";
                        }
                    }
                }
            }
        }
        model.addAttribute("title", "Create Event");
        model.addAttribute("errorMsg", "Login not successfull!");
        return "login/create";
    }

    public String convertPassword(String password)
    {
        String[] a=password.split("B",-2);
        return a[0].substring(0,a[0].length() -1);
    }

}
