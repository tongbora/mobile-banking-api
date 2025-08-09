package org.istad.mbanking.features.mail;

import org.istad.mbanking.features.mail.dto.MailRequest;
import org.istad.mbanking.features.mail.dto.MailResponse;

public interface MailService {
    MailResponse sendMail(MailRequest request);
    MailResponse sendMailWithTemplate(MailRequest request);
}
