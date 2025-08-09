package org.istad.mbanking.features.mail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.istad.mbanking.features.mail.dto.MailRequest;
import org.istad.mbanking.features.mail.dto.MailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {


    private final MailService mailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MailResponse sendMail(@Valid @RequestBody MailRequest request) {
        return mailService.sendMail(request);
    }

    @PostMapping("/templates")
    @ResponseStatus(HttpStatus.CREATED)
    public MailResponse sendMailWithTemplate(@Valid @RequestBody MailRequest request) {
        return mailService.sendMailWithTemplate(request);
    }
}
