package main.controller;

import main.domain.User;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationBindHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

import static main.controller.ControllerUtil.getErrorsMap;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @Valid User user,
            BindingResult bindingResult,
            Model model){
        if(user.getPassword() != null && !user.getPassword().equals(user.getPassword2())){
            model.addAttribute("passwordError", "Passwords are different!");
            model.addAttribute("user", user);
            return "registration";
        }

        if(bindingResult.hasErrors()){
            model.mergeAttributes(getErrorsMap(bindingResult));
            model.addAttribute("user", user);
            return "registration";

        }else {
            if (!userService.addUser(user)) {
                model.addAttribute("usernameError", "User exists!");
                model.addAttribute("user", user);
                return "registration";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable String code, Model model){
        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            model.addAttribute("message", "Activation success");
        }else{
            model.addAttribute("message", "Activation failed");
        }
        return "login";
    }
}
