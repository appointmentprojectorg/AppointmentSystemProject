package soft.appointment.persistence;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import soft.appointment.domain.User;
public class UserStorageTest {
    private final String File_name=new String("users.txt");
    private  UserStorage storage;
    @BeforeEach
    void setUp() {
        storage = new UserStorage();
        File file = new File(File_name);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void tearDown() {
        // نحذف الملف بعد كل اختبار
        File file = new File(File_name);
        if (file.exists()) {
            file.delete();
        }
    }
    @Test
    void testSaveUserAndGetUserByUsername(){
        User user = new User("yamen", "1234", "ADMIN");

        storage.saveUser(user);

        File file=new File(File_name);
        assertTrue(file.exists(), "File should exist after saving a user");
      User re=storage.getUserByUsername("yamen");
      assertNotNull(re);
      assertEquals("yamen",re.getUsername());
      assertEquals("1234",re.getPassword());
      assertEquals("ADMIN",re.getRole());

    }
    @Test
    void testGetUserByNameNotFound(){
        User re= storage.getUserByUsername("nameless");
        assertNull(re, "Should return null if user does not exist");

    }

    @Test
    void testSaveMultipleUsers() throws IOException{
        User user1=new User("ahmed","1234","USER");
        User user2=new User("ali","4321","ADMIN");

        storage.saveUser(user1);
        storage.saveUser(user2);
        long lines = Files.lines(new File(File_name).toPath()).count();
        assertEquals(2, lines, "File should contain two lines after saving two users");

        User retrievedAli = storage.getUserByUsername("ali");
        User retrievedAhmed = storage.getUserByUsername("ahmed");
        assertNotNull(retrievedAhmed);
        assertNotNull(retrievedAli);

        assertEquals("1234",retrievedAhmed.getPassword());
        assertEquals("4321",retrievedAli.getPassword());
    }

}
