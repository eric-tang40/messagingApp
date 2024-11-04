package java_files;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunLocalTest implements SharedResources {

    private UserManager userManager = new UserManager();

    // Authenticator Test Cases
    @Test
    public void testAuthenticateWithValidCredentials() {
        // Flush the database
        manager.deleteUsersStartingFrom();
        // Enter a user with the credentials
        userManager.createUser("validUser", "validPassword", "", "", null);

        // Assume valid credentials for testing
        boolean result = Authenticator.authenticate("validUser", "validPassword");
        assertTrue(result, "Authentication should succeed with valid credentials.");

        String userData = userManager.getUser("validUser");
        // Check that the username is equal to validUser
        assertTrue(userData.contains("\"username\":\"validUser\""), "User data should contain the correct username.");
        // We are not checking the password as it should not be accessible.


    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        // Attempt to authenticate with invalid credentials
        manager.deleteUsersStartingFrom();
        userManager.createUser("invalidUser", "invalidPassword", "", "", null);
        boolean result = Authenticator.authenticate("invalidUser", "actualinvalidPassword");
        assertFalse(result, "Authentication should fail with invalid credentials.");
        String userData = userManager.getUser("invalidUser");
        //assertTrue(userData.contains("\"username\":\"invalidUser\""), "User data should contain the correct username.");

    }
    // UserManager Test Cases
    @Test
    public void testCreateUser() {
        // Flush the database
        manager.deleteUsersStartingFrom();

        // Create a user
        String result = userManager.createUser("newUser", "userPassword", "user@example.com", "This is a new user.", null);
        assertEquals("User created successfully: ", result.substring(0, 27), "User should be created successfully.");

        // Check that the user is retrievable
        String userData = userManager.getUser("newUser");
        assertTrue(userData.contains("\"username\":\"newUser\""), "User data should contain the correct username.");
        // Check that the user data contains the correct email
        assertTrue(userData.contains("\"email\":\"user@example.com\""), "User data should contain the correct email.");

        // Check that the user data contains the correct bio
        assertTrue(userData.contains("\"bio\":\"This is a new user.\""), "User data should contain the correct bio.");
    }

    @Test
    public void testEditUser() {
        // Flush the database
        manager.deleteUsersStartingFrom();

        // Create a user to edit
        String userCreate = userManager.createUser("editUser", "editPassword", "edit@example.com",
                "This user will be edited.", null);
        assertTrue(userCreate.contains("\"username\":\"editUser\""), "User was not created successfully.");
        // Edit the user
        userManager.editUser("editUser", "newPassword", "newEmail@example.com", "Updated bio.", null);
        String userData = userManager.getUser("editUser");
        assertTrue(userData.contains("\"email\":\"newEmail@example.com\""), "User data should reflect the updated email.");
        assertTrue(userData.contains("\"bio\":\"Updated bio.\""), "User data should reflect the updated email.");
        // We are not checking the password as it should not be accessible.
    }
    
    @Test
    public void testEditUserWithInvalidUser() {
        // Flush the database
        manager.deleteUsersStartingFrom();

        // Create a user to edit
        String userCreate = userManager.createUser("editUser2", "editPassword", "", "", null);
        assertTrue(userCreate.contains("\"username\":\"editUser2\""), "User was not created successfully.");
        String a = userManager.editUser("111111", "newPassword", "newEmail@example.com", "Updated bio.", null);
        assertTrue(a.contains("could not be found"), "User should not be found.");
    }

    @Test
    public void testDeleteUser() {
        manager.deleteUsersStartingFrom();

        // Create a user to delete
        userManager.createUser("deleteUser", "deletePassword", "delete@example.com", "This user will be deleted.",
                null);

        // Delete the user
        userManager.deleteUser("deleteUser");
        String userData = userManager.getUser("deleteUser");
        assertTrue(userData.contains("could not be found"), "User should not be retrievable after deletion.");
    }

    @Test
    public void testGetUser() {
        // Flush the database
        manager.deleteUsersStartingFrom();

        // Create a user to retrieve
        String userContent = userManager.createUser("getUser", "getUserPassword", "1", "2", null);
        assertTrue(userContent.contains("User created successfully"), "User data should contain the correct username.");
        String userData = userManager.getUser("getUser");
        assertTrue(userData.contains("\"username\":\"getUser\""), "User data should contain the correct password.");
        assertTrue(userData.contains("\"email\":\"1\""), "User data should contain the correct email.");
        assertTrue(userData.contains("\"bio\":\"2\""), "User data should contain the correct bio.");
    }

    @Test
    public void testDeleteAll() {
        String a = manager.deleteUsersStartingFrom();
        assertTrue(a.contains("Stopping"), "No users should be found.");
    }
}
