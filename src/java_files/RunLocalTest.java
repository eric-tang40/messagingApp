package java_files;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunLocalTest {

    private UserManager userManager = new UserManager();

    // Authenticator Test Cases
    @Test
    public void testAuthenticateWithValidCredentials() {
        // Flush the database
        userManager.flushDatabase(); // Assume this method exists for cleanup

        // Enter a user with the credentials
        userManager.createUser("validUser", "validPassword", "", "", null);

        // Assume valid credentials for testing
        boolean result = Authenticator.authenticate("validUser", "validPassword");
        assertTrue(result, "Authentication should succeed with valid credentials.");

        String userData = userManager.getUser("validUser");
        // Check that the username is equal to validUser
        assertTrue(userData.contains("\"username\":\"validUser\""), "User data should contain the correct username.");
        // We are not checking the password as it should not be accessible.

        // Attempt to authenticate with invalid credentials
        boolean result1 = Authenticator.authenticate("invalidUser", "wrongPassword");

        // Assert that authentication fails
        assertFalse(result1, "Authentication should fail with invalid credentials.");

        // Check that "invalidUser" is not in the idTracker HashMap
        assertFalse(userManager.idTracker.containsKey("invalidUser"), "idTracker should not contain 'invalidUser' as a valid username.");

    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        // Attempt to authenticate with invalid credentials
        userManager.flushDatabase();
        userManager.createUser("invalidUser", "invalidPassword", "", "", null);
        boolean result = Authenticator.authenticate("invalidUser", "invalidPassword");
        assertFalse(result, "Authentication should fail with invalid credentials.");
        String userData = userManager.getUser("newUser");
        assertTrue(userData.contains("\"username\":\"newUser\""), "User data should contain the correct username.");

    }

    // UserManager Test Cases
    @Test
    public void testCreateUser() {
        // Flush the database
        userManager.flushDatabase(); // Assume this method exists for cleanup

        // Create a user
        String result = userManager.createUser("newUser", "userPassword", "user@example.com", "This is a new user.", null);
        assertEquals("User created successfully: ", result.substring(0, 29), "User should be created successfully.");

        // Check that the user is retrievable
        String userData = userManager.getUser("newUser");
        assertTrue(userData.contains("\"username\":\"newUser\""), "User data should contain the correct username.");
        // Check that the user data contains the correct email
        assertTrue(userData.contains("\"email\":\"newuser@example.com\""), "User data should contain the correct email.");

        // Check that the user data contains the correct bio
        assertTrue(userData.contains("\"bio\":\"This is my bio.\""), "User data should contain the correct bio.");
    }

    @Test
    public void testEditUser() {
        // Flush the database
        userManager.flushDatabase();

        // Create a user to edit
        userManager.createUser("editUser", "editPassword", "edit@example.com", "This user will be edited.", null);

        // Edit the user
        userManager.editUser("editUser", "newPassword", "newEmail@example.com", "Updated bio.", null);
        String userData = userManager.getUser("editUser");
        assertTrue(userData.contains("\"email\":\"newEmail@example.com\""), "User data should reflect the updated email.");
        // We are not checking the password as it should not be accessible.
    }

    @Test
    public void testDeleteUser() {
        // Flush the database
        userManager.flushDatabase();

        // Create a user to delete
        userManager.createUser("deleteUser", "deletePassword", "delete@example.com", "This user will be deleted.", null);

        // Delete the user
        userManager.deleteUser("deleteUser");
        String userData = userManager.getUser("deleteUser");
        assertTrue(userData.contains("could not be found"), "User should not be retrievable after deletion.");
    }
}
