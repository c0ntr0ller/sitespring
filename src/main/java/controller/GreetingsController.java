package controller;

import model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repo.MessageRepository;

import java.util.Map;

@Controller
public class GreetingsController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greetings")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model
    ){
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }
}
