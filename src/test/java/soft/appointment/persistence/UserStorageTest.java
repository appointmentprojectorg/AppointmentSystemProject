/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.persistence;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import soft.appointment.domain.User;

/**
 *
 * @author user
 */
public class UserStorageTest {
    private UserStorage storage = new UserStorage("test_users.txt");

    @AfterEach
    void cleanup() {
        new File("test_users.txt").delete();
    }

    @Test
    void testSaveAndGetUser() {
        User user = new User("testUser", "pass123", "USER");
        storage.saveUser(user);
        
        User retrieved = storage.getUserByUsername("testUser");
        assertNotNull(retrieved, "User should be found in the file");
        assertEquals("testUser", retrieved.getUsername());
        assertEquals("pass123", retrieved.getPassword());
    }
    @Test
void testGetUserByUsernameNotFound() {
    User result = storage.getUserByUsername("nonExistentUser");
    assertNull(result, "Should return null if user is not found");
}

@Test
void testSaveUserIOException() {
    UserStorage badStorage = new UserStorage("Z:/invalid_path/users.txt");
    
    assertDoesNotThrow(() -> badStorage.saveUser(new User("test", "pass", "USER")));
}
}
