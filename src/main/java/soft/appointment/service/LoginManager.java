package soft.appointment.service;

import soft.appointment.domain.User;
import soft.appointment.persistence.UserStorage;

/**
 * Manages the login and logout process for the system and the authentication during that.
 * 
 * @author MoumenAbuAyyash1
 * @version 1.0
 */
public class LoginManager {
    
    /**  used to find users in the file */
    private UserStorage storage = new UserStorage();
    
    /** Keeps track of the person currently using the system */
    private User currentUser = null;

    /**
     * Attempts to log a user into the system.
     * 
     * @param username the username entered 
     * @param password the password entered 
     * @return true if the login is successful
     */
    public boolean login(String username, String password) {
        // Ask the storage for a user with this name
        User userFromFile = storage.getUserByUsername(username);
        
        // Check if user exists and if the password matches
        if (userFromFile != null && userFromFile.getPassword().equals(password)) {
            //we check if the user is currently logged in and if yes we kick the old session by logging out
             if (this.currentUser != null) {
                this.logout(); 
            }
            // we set the current user to the one who logged in
            this.currentUser = userFromFile;
            return true;
        }
        
        // If it reaches here, the login failed
        return false;
    }

    /**
     * Clears the current user session (Logout).
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Gets the user who is currently logged in.
     * 
     * @return the current User object, or null if nobody is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    /**
     * Creates a new user in the system.
     * 
     * @param username the new name
     * @param password the new password
     * @param confirmPassword the second password entry for validation
     * @param role the role (ADMIN or USER)
     * @return 1 for success, 0 for mismatch, -1 for taken
     */
    public int registerUser(String username, String password, String confirmPassword, String role) {
        // we start by checking if the username is already taken first
        if (storage.getUserByUsername(username) != null) {
            return -1; // name already used
        }
        if (!password.equals(confirmPassword)) {
            return 0; // Failure code for "Mismatch"
        }
        
        // If it's a new name, save them to the file
        User newUser = new User(username, password, role);
        storage.saveUser(newUser);
        return 1;
    }
}