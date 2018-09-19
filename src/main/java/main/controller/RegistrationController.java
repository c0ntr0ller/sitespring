package main.controller;

import main.domain.User;
import main.domain.dto.CaptchaResponseDTO;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

import static main.controller.ControllerUtil.getErrorsMap;

@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String password2,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model){

        String url = String.format(CAPTCHA_URL, secret, recaptchaResponse);
        final CaptchaResponseDTO responseDTO = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

        if(!responseDTO.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha!");
        }

        boolean isPassword2Empty = StringUtils.isEmpty(password2);
        if(isPassword2Empty){
            model.addAttribute("password2Error", "Password confirmation can not be empty!");
        }

        if(user.getPassword() != null && !user.getPassword().equals(password2)){
            model.addAttribute("passwordError", "Passwords are different!");
            model.addAttribute("user", user);
            return "registration";
        }

        if(bindingResult.hasErrors() || isPassword2Empty || !responseDTO.isSuccess()){
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
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Activation success");
        }else{
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation failed");
        }
        return "login";
    }
}
