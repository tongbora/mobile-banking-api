package org.istad.mbanking.features.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mbanking.features.mail.dto.MailRequest;
import org.istad.mbanking.features.mail.dto.MailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{


    // we need to inject this dependency, and also configure it
    // config at application.properties
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Override
    public MailResponse sendMail(MailRequest request) {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(request.to());
            helper.setSubject(request.subject());
            helper.setText(request.body());
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong!"
            );
        }
        javaMailSender.send(message);
        return new MailResponse("Message sent");
    }

    public MailResponse sendMailWithTemplate(MailRequest request) {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        Context context = new Context();
        context.setVariable("name", "Tong Bora");
        String htmlTemplate = templateEngine.process("mail/welcome", context);

        try {
            helper.setTo(request.to());
            helper.setSubject(request.subject());
            helper.setText(htmlTemplate, true);
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong!"
            );
        }
        javaMailSender.send(message);
        return new MailResponse("Message sent");
    }
}
