package com.example.springredditclone.service;


import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.model.NotificationEmail;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    
    private final JavaMailSender mailSender;
    //private final MailContentBuilder mailContentBuilder;

    @Async
    void senMail(NotificationEmail notificationEmail){

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            //messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
            messageHelper.setText(notificationEmail.getBody());
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Email sent!!");
            // log.info(notificationEmail.getBody());
        } catch(MailException e){
            throw new SpringRedditException("Exception occured when sending mail to "+notificationEmail.getRecipient());
        }

    }
}