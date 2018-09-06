package main.controller;

import main.models.Message;
import main.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String messageAdd(@RequestParam(name = "text", required = true) String text,
                             @RequestParam(name = "tag", required = false) String tag,
                             Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam(name = "filter", required = false) String filter, Map<String, Object> model){

        Iterable<Message> messages;
        if(filter == null || filter.isEmpty()){
            messages = messageRepository.findAll();
        }else {
            messages = messageRepository.findByTextIsContainingOrTagIsContaining(filter, filter);
        }
        model.put("messages", messages);

        return "main";
    }
}
