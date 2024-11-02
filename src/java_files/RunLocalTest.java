package java_files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RunLocalTest {

    private UserManager userManager;

    @BeforeEach
    public void setup() {
        userManager = new UserManager();
    }

    @Test
    public void testAuthenticateWithValidCredentials() {
        // Assume valid credentials for testing
        boolean result = Authenticator.authenticate("validUser", "validPassword");
        assertTrue(result, "Authentication should succeed with valid credentials.");
    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        boolean result = Authenticator.authenticate("invalidUser", "invalidPassword");
        assertFalse(result, "Authentication should fail with invalid credentials.");
    }

    @Test
    public void testPopulateHashMap() {
        String result = userManager.populateHashMap();
        assertEquals("HashMap populated successfully with data from the backend.", result,
                "The hashmap should be populated successfully.");
    }

    @Test
    public void testCreateUserSuccessfully() {
        HashMap<String, ArrayList<String>> friends = new HashMap<>();
        String result = userManager.createUser("newUser", "password123", "user@example.com", "Hello, I'm new!", friends);
        assertTrue(result.startsWith("User created successfully:"), "User should be created successfully.");
    }

    @Test
    public void testCreateUserWithExistingUsername() {
        HashMap<String, ArrayList<String>> friends = new HashMap<>();
        userManager.createUser("existingUser", "password123", "user@example.com", "I'm here!", friends);
        String result = userManager.createUser("existingUser", "newPassword", "newuser@example.com", "I'm still here!", friends);
        assertEquals("A user with that username already exists.", result, "Creating user with existing username should fail.");
    }

    @Test
    public void testGetUserSuccessfully() {
        userManager.createUser("getUser", "password123", "user@example.com", "Just checking!", new HashMap<>());
        String result = userManager.getUser("getUser");
        assertTrue(result.contains("User Data:"), "User data should be retrieved successfully.");
    }

    @Test
    public void testGetUserNotFound() {
        String result = userManager.getUser("nonExistentUser");
        assertEquals("User nonExistentUser could not be found.", result, "Should return user not found message.");
    }

    @Test
    public void testEditUserSuccessfully() {
        userManager.createUser("editUser", "password123", "user@example.com", "Editable user.", new HashMap<>());
        userManager.editUser("editUser", "newPassword", "newEmail@example.com", "Updated bio", new HashMap<>());
        String result = userManager.getUser("editUser");
        assertTrue(result.contains("newEmail@example.com"), "User's email should be updated successfully.");
    }

    @Test
    public void testDeleteUserSuccessfully() {
        userManager.createUser("deleteUser", "password123", "user@example.com", "To be deleted", new HashMap<>());
        userManager.deleteUser("deleteUser");
        String result = userManager.getUser("deleteUser");
        assertEquals("User deleteUser could not be found.", result, "User should be deleted successfully.");
    }

    @Test
    public void testOutputDatabase() {
        ArrayList<String> dbOutput = userManager.outputDatabase();
        assertNotNull(dbOutput, "Database output should not be null.");
        // Further assertions can be added based on expected output format.
    }
}
