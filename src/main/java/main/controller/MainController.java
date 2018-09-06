package main.controller;

import main.domain.Message;
import main.domain.User;
import main.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Model model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(name = "filter", required = false) String filter, Model model){
        Iterable<Message> messages = messageRepository.findAll();

        if(filter == null || filter.isEmpty()){
            messages = messageRepository.findAll();
        }else {
            messages = messageRepository.findByTextIsContainingOrTagIsContaining(filter, filter);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String messageAdd(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "text", required = true) String text,
            @RequestParam(name = "tag", required = false) String tag,
            Model model){
        Message message = new Message(text, tag, user);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }
}
