package kg.amanturov.forum.service;


import kg.amanturov.forum.entity.EmailDetails;

public interface EmailService {


    String sendSimpleMail(EmailDetails details);


    String sendMailWithAttachment(EmailDetails details);
}