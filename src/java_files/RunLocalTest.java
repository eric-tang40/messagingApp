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
        // Authenticating invalid credentials will always return false.
        userManager.flushDatabase();
        userManager.createUser("invalidUser", "invalidPassword", "", "", null);
        boolean result = Authenticator.authenticate("invalidUser", "invalidPassword");
        assertFalse(result, "Authentication should fail with invalid credentials.");
        String userData = userManager.getUser("newUser");
        assertTrue(userData.contains("\"username\":\"newUser\""), "User data should contain the correct username.");

    }

    // UserManager Test Cases

    @Test
    public void testCreateUserWithValid() {
        // Flush the database
        userManager.flushDatabase(); // Assume this method exists for cleanup

        // Create a user
        String result = userManager.createUser("newUser", "userPassword", "user@example.com", "This is a new user.", null);
        assertEquals("User created successfully: ", result.substring(0, 29), "User should be created successfully.");

        // Check that the user is retrievable
        String userData = userManager.getUser("newUser");
        assertTrue(userData.contains("\"username\":\"newUser\""), "User data should contain the correct username.");
        assertTrue(userData.contains("\"email\":\"user@example.com\""), "User data should contain the correct email.");
        assertTrue(userData.contains("\"bio\":\"This is a new user.\""), "User data should contain the correct bio.");
        // Password should not be checked as it's not accessible
        assertTrue(userData.contains("\"friends\":{}"), "User data should contain an empty friends list.");
    }

    @Test
    public void testCreateUserWithInvalid() {
        // Flush the database
        userManager.flushDatabase();

        // Attempt to create a user with an existing username
        userManager.createUser("existingUser", "password", "existing@example.com", "This user exists.", null);
        String result = userManager.createUser("existingUser", "newPassword", "new@example.com", "This should fail.", null);

        // Assert that the creation fails
        assertEquals("A user with that username already exists.", result, "User creation should fail due to duplicate username.");
    }

    @Test
    public void testEditUserWithValid() {
        // Flush the database
        userManager.flushDatabase();

        // Create a user to edit
        userManager.createUser("editUser", "editPassword", "edit@example.com", "This user will be edited.", null);

        // Edit the user
        userManager.editUser("editUser", "newPassword", "newEmail@example.com", "Updated bio.", null);
        String userData = userManager.getUser("editUser");

        assertTrue(userData.contains("\"username\":\"editUser\""), "User data should reflect the correct username.");
        assertTrue(userData.contains("\"email\":\"newEmail@example.com\""), "User data should reflect the updated email.");
        // Password should not be checked as it's not accessible
        assertTrue(userData.contains("\"bio\":\"Updated bio.\""), "User data should reflect the updated bio.");
        assertTrue(userData.contains("\"friends\":{}"), "User data should contain an empty friends list.");
    }

    @Test
    public void testEditUserWithInvalid() {
        // Flush the database
        userManager.flushDatabase();

        // Attempt to edit a non-existing user
        String result = userManager.editUser("nonExistentUser", "newPassword", "newEmail@example.com", "Updated bio.", null);
        // Assuming editUser handles non-existent users internally, we would typically expect no output or a message
        assertEquals("User nonExistentUser could not be found.", result, "Editing a non-existent user should return an error message.");
    }

    @Test
    public void testDeleteUserWithValid() {
        // Flush the database
        userManager.flushDatabase();

        // Create a user to delete
        userManager.createUser("deleteUser", "deletePassword", "delete@example.com", "This user will be deleted.", null);

        // Delete the user
        userManager.deleteUser("deleteUser");
        String userData = userManager.getUser("deleteUser");
        assertTrue(userData.contains("could not be found"), "User should not be retrievable after deletion.");
    }

    @Test
    public void testDeleteUserWithInvalid() {
        // Flush the database
        userManager.flushDatabase();

        // Attempt to delete a non-existing user
        String result = userManager.deleteUser("nonExistentUser");

        // Assert that the delete operation failed and returned the expected message
        assertEquals("User nonExistentUser could not be found.", result, "Deleting a non-existent user should return an error message.");
    }

    @Test
    public void testCreateUserMessage() {
        String message = "user-123-Hey! This is my test message";
        UserMessage test = new UserMessage(message);
        assertEquals(test.toString(), "user-123-Hey! This is my test message");
    }

    @Test
    public void testUserKey() {
        String message = "user-123-Hey! This is my test message";
        UserMessage test = new UserMessage(message);
        assertEquals(test.getUserKey(), "user");
    }

    @Test
    public void testHistoryMarker() {
        String message = "user-123-Hey! This is my test message";
        UserMessage test = new UserMessage(message);
        assertTrue(test.getHistoryMarker() == 123);
    }

    @Test
    public void testContent() {
        String message = "user-123-Hey! This is my test message";
        UserMessage test = new UserMessage(message);
        assertEquals(test.getContent(), "Hey! This is my test message");
    }

    @Test
    public void testIsLiked() {
        String message = "user-123-Hey! This is my test message";
        UserMessage test = new UserMessage(message);
        assertFalse(test.getIsLiked());
    }
}

