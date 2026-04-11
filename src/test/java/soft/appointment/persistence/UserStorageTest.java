package soft.appointment.persistence;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class UserStorageTest {

    private final String TEST_FILE = "test_users.txt";
    private UserStorage storage;

    @BeforeEach
    void setUp() {
        storage = new UserStorage(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    
    @Test
    void testSaveAndGetSuccessful() {
        User user = new User("moumen", "pass123", "ADMIN", "moumen@example.com");
        storage.saveUser(user);

        User retrieved = storage.getUserByUsername("moumen");

        assertNotNull(retrieved);
        assertEquals("moumen", retrieved.getUsername());
        assertEquals("pass123", retrieved.getPassword());
        assertEquals("ADMIN", retrieved.getRole());
        assertEquals("moumen@example.com", retrieved.getEmail());
    }

   
    @Test
    void testCaseInsensitivity() {
        User user = new User("JohnDoe", "pw", "USER", "john@test.com");
        storage.saveUser(user);

        User retrieved = storage.getUserByUsername("johndoe"); // Lowercase search
        assertNotNull(retrieved);
        assertEquals("JohnDoe", retrieved.getUsername());
    }

    
    @Test
    void testUserNotFound() {
        User retrieved = storage.getUserByUsername("ghost_user");
        assertNull(retrieved);
    }

    
    @Test
    void testEmailFallbackLogic() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TEST_FILE))) {
            pw.println("oldUser,oldPass,USER"); 
        }

        User retrieved = storage.getUserByUsername("oldUser");
        assertNotNull(retrieved);
        assertEquals("oldUser", retrieved.getEmail());
    }

   
    @Test
    void testMalformedLinesHandling() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TEST_FILE))) {
            pw.println("broken_line_with_no_commas"); 
            pw.println("validUser,pass,ADMIN,valid@email.com");
        }

        User retrieved = storage.getUserByUsername("validUser");
        assertNotNull(retrieved, "Should skip the broken line and find the valid one");
        assertEquals("validUser", retrieved.getUsername());
    }

  
    @Test
    void testConstructors() {
        UserStorage defaultStorage = new UserStorage();
        assertNotNull(defaultStorage);
        
        UserStorage customStorage = new UserStorage("another_test.txt");
        assertNotNull(customStorage);
        new File("another_test.txt").delete();
    }
}