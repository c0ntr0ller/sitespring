package main.controller;

import main.domain.Message;
import main.domain.User;
import main.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        Message message = new Message(text, tag, user);

        if(file != null){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            message.setFilename(resultFileName);

            file.transferTo(new File(uploadPath + File.separatorChar + resultFileName));
        }

        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }
}
