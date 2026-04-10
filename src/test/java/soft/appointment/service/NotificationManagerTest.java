package soft.appointment.service;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.User;

public class NotificationManagerTest {

    @Test
    void testNotifyAllSendsMessageToObservers() {
        NotificationManager nm = new NotificationManager();
       
        Observer mockObserver = mock(Observer.class);
        
        nm.addObserver(mockObserver);
        
        User testUser = new User("moumen", "pw", "USER", "moumen@test.com");
        String msg = "Reminder: Your appointment is tomorrow!";

        nm.notifyAll(testUser, msg);

        verify(mockObserver, times(1)).notify(testUser, msg);
    }
}