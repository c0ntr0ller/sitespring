package main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    
    @Value("${spring.mail.host:\"smtp.yandex.ru\"}")
    private String host = "smtp.yandex.ru";

    @Value("${spring.mail.username:\"c0ntr0ller@yandex.ru\"}")
    private String username;

    @Value("${spring.mail.password:\"Jupiter5\"}")
    private String password;

    @Value("${spring.mail.protocol:\"smtp\"}")
    private String protocol;

    @Value("${spring.mail.port:465}")
    private int port;

//    @Value("${mail.auth:\"true\"}")
//    private String auth;

    @Value("${mail.debug:\"true\"}")
    private String debug;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", debug);

        return mailSender;
    }
}
