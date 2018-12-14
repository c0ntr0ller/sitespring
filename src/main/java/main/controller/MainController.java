package main.controller;

import main.domain.Message;
import main.domain.User;
import main.repo.MessageRepository;
import main.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

import static main.controller.ControllerUtil.getErrorsMap;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public String greeting(Model model){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(name = "filter", required = false) String filter,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        Page<Message> page;

        if(filter == null || filter.isEmpty()){
            page = messageRepository.findAllByOrderByIdDesc(pageable);
        }else {
            page = messageRepository.findByTextIsContainingOrTagIsContaining(filter, filter, pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping({"/main", "/user-messages/{user}"})
    public String messageAdd(
            @AuthenticationPrincipal User currentUser,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        message.setAuthor(currentUser);
        if(bindingResult.hasErrors()){

            model.mergeAttributes(getErrorsMap(bindingResult));
            model.addAttribute("message", message);

        }else {
            message.setFilename(fileService.saveFile(file));

            model.addAttribute("message", null);

            messageRepository.save(message);
        }
        Page<Message> page = messageRepository.findAllByOrderByIdDesc(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");

        return "main";
    }

    @GetMapping("/user-messages/{user}")
    public String getUserMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
            ){
        Set<Message> messages = user.getMessages();
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("message", message);
        return "usermessages";
    }
}
