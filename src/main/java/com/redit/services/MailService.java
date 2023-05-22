package com.redit.services;


import com.redit.exceptions.SpingReddiExp;
import com.redit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final MailContentBuilder mailContentBuilder;

    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(NotificationEmail notificationEmail) throws SpingReddiExp {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("bellafki19@gmail.com");
        message.setTo(notificationEmail.getRecepient());
        message.setSubject(notificationEmail.getSubject());
        message.setText(notificationEmail.getBody());
        javaMailSender.send(message);
        log.info("mail send success ***********************");
    }
}
