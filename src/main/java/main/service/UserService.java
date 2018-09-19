package main.service;

import main.domain.Role;
import main.domain.User;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${activation.address:\"http://localhost\"}")
    private String serverAddress;

    @Value("${server.port:8081}")
    private String serverPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findUserByUsername(user.getUsername());

        if(userFromDb != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendActivationMail(user);

        return true;
    }

    private void sendActivationMail(User user) {
        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s!\n You activation link is %s:%s/activate/%s",
                    user.getUsername(),
                    serverAddress,
                    serverPort,
                    user.getActivationCode());
            mailSender.sendEmail(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user != null){
            user.setActive(true);
            user.setPassword2(user.getPassword());
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void saveUser(User user, String userName, Map<String, String> formdata) {
        user.setUsername(userName);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : formdata.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void updateProfile(User user, String password, String email) {
        user.setPassword(passwordEncoder.encode(password));


        boolean isEmailChanged = (user.getEmail() != null && !user.getEmail().equals(email)) ||
                (email != null && !email.equals(user.getEmail()));

        if(isEmailChanged){
            user.setEmail(email);

            if(email != null && !email.isEmpty()){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        userRepository.save(user);

        if(isEmailChanged) {
            sendActivationMail(user);
        }
    }
}
