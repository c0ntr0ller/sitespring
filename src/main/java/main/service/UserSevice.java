package main.service;

import main.domain.Role;
import main.domain.User;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserSevice implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Value("${activation.address:\"http://localhost\"}")
    private String serverAddress;

    @Value("${server.port:8081}")
    private String serverPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findUserByUsername(user.getUsername());

        if(userFromDb != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello, %s!\n You activation link is %s:%s/activate/%s",
                    user.getUsername(),
                    serverAddress,
                    serverPort,
                    user.getActivationCode());
            mailSender.sendEmail(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user != null){
            user.setActive(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
