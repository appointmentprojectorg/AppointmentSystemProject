package soft.appointment.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.User;
import soft.appointment.persistence.UserStorage;
import java.io.File;

public class LoginManagerTest {

    private LoginManager loginManager;
    private final String TEST_FILE = "test_login_users.txt";

    @BeforeEach
    void setUp() {
        UserStorage storage = new UserStorage(TEST_FILE);
        loginManager = new LoginManager(storage);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testRegistrationAndLoginFlow() {
        int regResult = loginManager.registerUser("moumen", "p123", "p123", "ADMIN", "m@test.com");
        assertEquals(1, regResult);

        boolean loginSuccess = loginManager.login("moumen", "p123");
        assertTrue(loginSuccess);
        assertNotNull(loginManager.getCurrentUser());
        assertEquals("moumen", loginManager.getCurrentUser().getUsername());
    }

    @Test
    void testLoginFailure() {
        loginManager.registerUser("ali", "123", "123", "USER", "ali@test.com");
        
        assertFalse(loginManager.login("ali", "wrong_pass"));
        assertFalse(loginManager.login("non_existent", "123"));
    }

    @Test
    void testRegistrationFailures() {
        int mismatch = loginManager.registerUser("u", "p1", "p2", "USER", "e");
        assertEquals(0, mismatch);

        loginManager.registerUser("taken", "1", "1", "USER", "e");
        int taken = loginManager.registerUser("taken", "1", "1", "USER", "e");
        assertEquals(-1, taken);
    }

    @Test
    void testLogoutAndSession() {
        loginManager.registerUser("user", "pass", "pass", "USER", "e");
        loginManager.login("user", "pass");
        
        loginManager.logout();
        assertNull(loginManager.getCurrentUser());
    }
    
    @Test
    void testConstructors() {
        assertNotNull(new LoginManager());
    }
}