package com.dempseywood.service;

import com.dempseywood.config.ApplicationProperties;
import com.dempseywood.domain.EmailSubscription;
import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.User;

import com.dempseywood.domain.enumeration.Event;
import com.dempseywood.domain.enumeration.RecipientType;
import com.dempseywood.repository.EmailSubscriptionRepository;
import io.github.jhipster.config.JHipsterProperties;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.stream.Collector;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String NIGGLE = "niggle";

    private static final String BASE_URL = "baseUrl";

    private static final String FORMATTER = "formatter";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final ApplicationProperties applicationProperties;

    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine,
                       ApplicationProperties applicationProperties, EmailSubscriptionRepository emailSubscriptionRepository) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.applicationProperties = applicationProperties;
        this.emailSubscriptionRepository = emailSubscriptionRepository;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);

    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendOnHoldNotificationMail(Niggle niggle, String username) {
        String email = applicationProperties.getNotification().getOnHold().getTo();
        log.debug("Sending on hold notification email to '{}'", email);
        sendNiggleEmailFromTemplate(Event.ON_HOLD, niggle,"onHoldNotificationEmail", "email.onhold.title", username);
    }

    private void sendNiggleEmailFromTemplate(Event event, Niggle niggle, String templateName, String titleKey, String username) {
        Locale locale = Locale.getDefault();
        Context context = new Context();
        context.setVariable(NIGGLE, niggle);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(USER, username);
        DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                .withLocale( Locale.ENGLISH )
                .withZone( ZoneId.of("NZ") );
        context.setVariable(FORMATTER, formatter);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        //String[] to =
        String[] to =   emailSubscriptionRepository
            .findByEventAndRecipientType(event, RecipientType.TO)
            .stream().map(emailSub -> emailSub.getEmail()).toArray(String[]::new);
        if(to == null || to.length == 0){
            to = new String[1];
            if(event.equals(Event.HIGH_PRIORITY)){
                to[0] = applicationProperties.getNotification().getHighPriority().getTo();
            }
            if(event.equals(Event.ON_HOLD)){
                to[0] = applicationProperties.getNotification().getOnHold().getTo();
            }
        }
        String[] cc =   emailSubscriptionRepository
            .findByEventAndRecipientType(event, RecipientType.CC)
            .stream().map(emailSub -> emailSub.getEmail()).toArray(String[]::new);
        String[] bcc =   emailSubscriptionRepository
            .findByEventAndRecipientType(event, RecipientType.BCC)
            .stream().map(emailSub -> emailSub.getEmail()).toArray(String[]::new);
        sendNotificationEmail(to, cc, bcc, subject, content, false, true);
    }

    @Async
    public void sendHighPriorityNotificationMail(Niggle niggle, String username) {
        String email = applicationProperties.getNotification().getHighPriority().getTo();
        log.debug("Sending high priority notification email to '{}'", email);
        sendNiggleEmailFromTemplate(Event.HIGH_PRIORITY, niggle,"highPriorityFaultNotificationEmail", "email.highPriority.title", username);
    }

    @Async
    public void sendNotificationEmail(String[] to, String[] cc, String[] bcc, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            if(to != null && to.length > 0) {
                message.setTo(to);
            }
            if(cc != null && cc.length > 0){
                message.setCc(cc);
            }
            if(bcc != null && bcc.length > 0){
                message.setBcc(bcc);
            }

            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }
}
