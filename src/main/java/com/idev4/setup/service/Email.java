package com.idev4.setup.service;

import com.idev4.setup.domain.MwStpCnfigVal;
import com.idev4.setup.dto.EmailDto;
import com.idev4.setup.repository.MwStpCnfigValRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Properties;

@Component
public class Email {

    Logger logger = LoggerFactory.getLogger(Email.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MwStpCnfigValRepository mwStpCnfigValRepository;

    public void email(EmailDto emailDto) throws Exception {

        Multipart multipart = new MimeMultipart();

        emailDto = emailCredential(emailDto.emailCredentialCd, emailDto);

        Message message = new MimeMessage(getSession(emailDto));
        message.setFrom(new InternetAddress(emailDto.user));

        message.setRecipients(Message.RecipientType.TO, emailDto.to);
        message.setSubject(emailDto.subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(emailDto.body, "text/html");

        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        logger.info(emailDto.subject + " Email Sending please wait...");
        Transport.send(message);
        logger.info(emailDto.subject + " Email Send Successfully...");
    }

    private Session getSession(EmailDto emailDto) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.sendpartial", true);
        prop.put("mail.smtp.host", emailDto.host);
        prop.put("mail.smtp.port", emailDto.port);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDto.user, emailDto.password);
            }
        });
        return session;
    }

    private EmailDto emailCredential(String groupCode, EmailDto emailDto) {
        List<MwStpCnfigVal> lists = mwStpCnfigValRepository.findAllByStpGrpCdAndCrntRecFlgOrderBySortOrderAsc(groupCode, true);

        for (MwStpCnfigVal stp : lists) {
            if (stp.getStpValCd().equals("0001")) {
                emailDto.user = stp.getRefCdValDscr().split(":")[0];
            } else if (stp.getStpValCd().equals("0002")) {
                emailDto.password = stp.getRefCdValDscr();
            } else if (stp.getStpValCd().equals("0003")) {
                emailDto.host = stp.getRefCdValDscr();
            } else if (stp.getStpValCd().equals("0004")) {
                emailDto.port = Integer.parseInt(stp.getRefCdValDscr());
            }
        }
        return emailDto;
    }
}
