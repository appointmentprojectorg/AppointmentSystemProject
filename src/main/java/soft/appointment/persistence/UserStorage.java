package soft.appointment.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import soft.appointment.domain.User;

/**
 * The class responsible for storing the users into a file.
 * 
 * @author MoumenAbuAyyash1
 * @version 1.0
 */
public class UserStorage {

    /** The name of the file on the computer */
    private  String FILE_NAME = "users.txt";

    /**
     * Saves a user's information to the text file.
     * 
     * @param user the user object to be saved
     */
    public void saveUser(User user) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            // We save it in this format username,password,role...
writer.println(user.getUsername() + "," + user.getPassword() + "," + user.getRole() + "," + user.getEmail());        }
        catch (IOException e) { //
            
        }
    }
    public UserStorage() {
        this("users.txt");
    }
    
    public UserStorage(String fileName) {
        this.FILE_NAME = fileName;
    }

    
    /**
     * Reads the file and searches for a user by their username.
     * 
     * @param username the username to search for
     * @return a User object if found, or null if not found
     */
    public User getUserByUsername(String username) {
    try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(FILE_NAME))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            
            if (parts.length < 3) continue;

            if (parts[0].equalsIgnoreCase(username)) {
                String emailVal;
                if (parts.length >= 4) {
                    emailVal = parts[3]; 
                } else {
                    emailVal = parts[0]; 
                }

                return new User(parts[0], parts[1], parts[2], emailVal);
            }
        }
    } catch (java.io.IOException e) { }
    return null;
}
    
}