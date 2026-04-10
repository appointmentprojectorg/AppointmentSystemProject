package soft.appointment.service;

import soft.appointment.domain.User;

public class EmailNotificationService implements Observer {

    private final EmailService emailService;

    public EmailNotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(User user, String messageContent) {
        String subject = "Appointment System Notification";
        String body = "Dear " + user.getUsername() + ",\n\n" + messageContent;

        emailService.sendEmail(user.getEmail(), subject, body);
    }
}