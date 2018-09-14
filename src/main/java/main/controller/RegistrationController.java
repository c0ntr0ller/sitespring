package main.controller;

import main.domain.User;
import main.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    UserSevice userSevice;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
        if(!userSevice.addUser(user)){
            model.put("message", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model){
        boolean isActivated = userSevice.activateUser(code);
        if(isActivated){
            model.addAttribute("message", "Activation success");
        }else{
            model.addAttribute("message", "Activation failed");
        }
        return "login";
    }
}
