package soft.appointment.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import soft.appointment.domain.User;

public class EmailNotificationServiceTest {

    @Test
    void testNotifyCallsEmailService() {
        EmailService mockService = mock(EmailService.class);
        
        EmailNotificationService notificationService = new EmailNotificationService(mockService);
        
        User testUser = new User("moumen", "pw", "USER", "moumen@test.com");

        notificationService.notify(testUser, "Your appointment is confirmed.");

        verify(mockService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }
}