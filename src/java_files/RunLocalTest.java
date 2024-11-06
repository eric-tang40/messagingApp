package java_files;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunLocalTest implements SharedResources {

    // no instance variables
    // all resources you need are in sharedResources

    // Authenticator Test Cases
    @Test
    public void testAuthenticateWithValidCredentials() {
        // flush the database
        manager.flushDatabase();

        // create a user and ensure it was created successfully
        String userContent = manager.createUser("user", "pwd", "user@example.com", "user bio", null);
        assertTrue(userContent.contains("User created successfully"), "User was not created successfully.");

        // try to authenticate with valid credentials
        boolean a = authenticator.authenticate("user", "pwd");
        assertTrue(a, "User was successfully authenticated with an incorrect password.");
    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        // flush the database
        manager.flushDatabase();

        // create a user and ensure it was created successfully
        String userContent = manager.createUser("user", "pwd", "user@example.com", "user bio", null);
        assertTrue(userContent.contains("User created successfully"), "User was not created successfully.");

        // try to authenticate with invalid credentials
        boolean a = authenticator.authenticate("user", "wrongpwd");
        assertFalse(a, "User was successfully authenticated with an incorrect password.");
    }

    // UserManager Test Cases
    @Test
    public void testCreateUser() {
        // flush the database
        manager.flushDatabase();

        // create a user and ensure it was created successfully
        String userContent = manager.createUser("user", "pwd", "user@example.com", "user bio", null);
        assertTrue(userContent.contains("User created successfully"), "User was not created successfully.");

        // manually check that the user was made correctly
        String userData = manager.getUser("user");
        assertTrue(userData.contains("\"username\":\"user\""), "User data should contain the correct username.");
        assertTrue(userData.contains("\"email\":\"user@example.com\""), "User data should contain the correct email.");
        assertTrue(userData.contains("\"bio\":\"user bio\""), "User data should contain the correct bio.");

        // flush the database at the end to reset
        manager.flushDatabase();
    }

    @Test
    public void testEditUser() {
        // flush the database
        manager.flushDatabase();

        // create a user and ensure it was created successfully
        String userCreate = manager.createUser("user", "pwd", "", "", null);
        assertTrue(userCreate.contains("User created successfully"), "User was not created successfully.");

        // edit the user and check for success
        String userEdit = manager.editUser("user", "pwd", "email", "bio", null);
        assertTrue(userEdit.contains("User updated successfully."), "User was not updated successfully.");

        // double-check using manual comparisons
        String userData = manager.getUser("user");
        // Note: usernames cannot be changed, passwords are not saved in the database
        assertTrue(userData.contains("\"email\":\"email\""), "User email was not updated successfully.");
        assertTrue(userData.contains("\"bio\":\"bio\""), "User bio was not updated successfully.");

        // flush the database at the end to reset
        manager.flushDatabase();
    }

    @Test
    public void testEditUserWithInvalidUser() {
        // flush the database
        manager.flushDatabase();

        // create a user
        String userCreate = manager.createUser("user", "pwd", "", "", null);
        assertTrue(userCreate.contains("User created successfully"), "User was not created successfully.");

        // try to edit a user that doesn't exist
        String a = manager.editUser("nonExistentUser", "pwd", "email@example.com", "bio", null);
        assertTrue(a.contains("could not be found"), "User should not be found.");

        // flush the database at the end to reset
        manager.flushDatabase();
    }

    @Test
    public void testDeleteUser() {
        // flush the database
        manager.flushDatabase();

        // create a user to delete
        String userContent = manager.createUser("user", "pwd", "", "", null);
        assertTrue(userContent.contains("User created successfully"), "User was not created successfully.");

        // delete the user
        manager.deleteUser("user");

        // try to get the user. ensure the user cannot be found
        String userData = manager.getUser("user");
        assertTrue(userData.contains("could not be found"), "User should not be retrievable after deletion.");

        // flush the database at the end to reset
        manager.flushDatabase();
    }

    @Test
    public void testGetUser() {
        // flush the database
        manager.flushDatabase();

        // create a user to retrieve
        String userContent = manager.createUser("getUser", "getUserPassword", "1", "2", null);

        // ensure user was created
        assertTrue(userContent.contains("User created successfully"), "User was not created successfully.");

        // ensure getUser() returns the proper data
        String userData = manager.getUser("getUser");
        assertTrue(userData.contains("\"username\":\"getUser\""), "User data should contain the correct username.");
        assertTrue(userData.contains("\"email\":\"1\""), "User data should contain the correct email.");
        assertTrue(userData.contains("\"bio\":\"2\""), "User data should contain the correct bio.");

        // flush the database at the end to reset
        manager.flushDatabase();
    }

    @Test
    public void testFlushDatabase() {
        // flush the database
        manager.flushDatabase();

        // creating new users and ensure they were successfully created
        String user1content = manager.createUser("Eric", "pwd", "", "", null);
        String user2content = manager.createUser("George", "pwd", "", "", null);
        String user3content = manager.createUser("Bob", "pwd", "", "", null);
        assertTrue(user1content.contains("User created successfully"), "User was not created successfully.");
        assertTrue(user2content.contains("User created successfully"), "User was not created successfully.");
        assertTrue(user3content.contains("User created successfully"), "User was not created successfully.");

        // flush the database again, this time truly testing its functionality
        boolean flushed = manager.flushDatabase();
        assertTrue(flushed, "No users should be found.");

        // flush the database at the end to reset
        manager.flushDatabase();
    }
}
