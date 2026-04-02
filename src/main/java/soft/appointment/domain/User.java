package soft.appointment.domain;

/**
 * an Admin or a User in the system depending on the role.
 * 
 * @author MoumenAbuAyyash1
 * @version 1.0
 */
public class User {

    /** username */
    private String username;
    
    /** password */
    private String password;
    
    /** either administrator or user  */
    private String role;

    /**
     * 
     * 
     * @param username 
     * @param password 
     * @param role 
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the username.
     * @return the username string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     * @return the password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role.
     * @return the role string (ADMIN or USER)
     */
    public String getRole() {
        return role;
    }
}