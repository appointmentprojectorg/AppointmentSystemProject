package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import soft.appointment.service.LoginManager;
import soft.appointment.domain.User;
import soft.appointment.persistence.UserStorage;

/*
@ExtendWith(MockitoExtension.class)
public class LoginManagerTest {

    @Mock
    private UserStorage storage;

    @InjectMocks
    private LoginManager loginManager;

    @Test
    public void testLoginSuccess() {
        User fakeUser = new User("admin", "123", "ADMIN");
        when(storage.getUserByUsername("admin")).thenReturn(fakeUser);

        boolean result = loginManager.login("admin", "123");

        assertTrue(result);
        assertEquals(fakeUser, loginManager.getCurrentUser());
    }

    @Test
    public void testLoginFailure() {
        User fakeUser = new User("admin", "123", "ADMIN");
        when(storage.getUserByUsername("admin")).thenReturn(fakeUser);

        boolean result = loginManager.login("admin", "wrong_password");

        assertFalse(result);
    }

    @Test
    public void testLogout() {
        loginManager.logout();
        assertNull(loginManager.getCurrentUser());
    }

    @Test
    public void testLogoutLogicWithoutStorage() {
        LoginManager manager = new LoginManager(null);
        manager.logout();
        assertNull(manager.getCurrentUser(), "Logout should work without any external dependencies");
    }

    @Test
    public void testRegistrationPasswordMismatch() {
        int result = loginManager.registerUser("user", "pass1", "pass2", "USER");
        assertEquals(0, result);
    }

    @Test
    public void testRegistrationPasswordMismatchWithoutStorage() {
        LoginManager manager = new LoginManager(null);
        int result = manager.registerUser("user", "pass1", "pass2", "USER");
        assertEquals(0, result, "Should return 0 for password mismatch without touching storage");
    }

    @Test
    public void testRegisterUserUsernameTaken() {
        User existing = new User("admin", "123", "ADMIN");
        when(storage.getUserByUsername("admin")).thenReturn(existing);
        
        int result = loginManager.registerUser("admin", "123", "123", "ADMIN");
        assertEquals(-1, result);
    }

    @Test
    public void testRegisterUserSuccess() {
        when(storage.getUserByUsername("newuser")).thenReturn(null);

        int result = loginManager.registerUser("newuser", "p1", "p1", "USER");
        
        assertEquals(1, result);
        verify(storage, times(1)).saveUser(any(User.class));
    }
}
*/