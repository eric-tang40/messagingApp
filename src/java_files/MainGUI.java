package java_files;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application implements SharedResources {

    private Stage primaryStage; // the GUI being displayed

    @Override
    public void start(Stage primaryStage) {
        // this populates the hashmap that we use to save a local copy of a part of the database
        manager.populateHashMap(); // do not remove this line of code from the top

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Main Screen");

        // create grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // buttons
        Button createUserButton = new Button("Create User");
        Button loginButton = new Button("Login");

        // buttons actions
        createUserButton.setOnAction(e -> showCreateUser());
        loginButton.setOnAction(e -> showLogin());

        grid.add(createUserButton, 0, 0);
        grid.add(loginButton, 1, 0);

        // more setup
        VBox root = new VBox(grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // show the GUI
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showCreateUser() {
        CreateUserGUI createUserGUI = new CreateUserGUI();
        try {
            createUserGUI.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLogin() {
        LoginGUI loginGUI = new LoginGUI();
        try {
            loginGUI.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class CreateUserGUI extends Application implements SharedResources {

    private Stage primaryStage; // current GUI being displayed

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private TextField emailField = new TextField();
    private TextField bioField = new TextField();
    private Label terminalOutputLabel = new Label();
    private StringProperty terminalOutput = new SimpleStringProperty("");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Create User");

        // create grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // add labels and text fields
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);

        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(new Label("Bio:"), 0, 3);
        grid.add(bioField, 1, 3);

        // bind the terminal output label to its output
        terminalOutputLabel.textProperty().bind(terminalOutput);

        // buttons for creating a user
        Button submitButton = new Button("Submit");
        Button clearButton = new Button("Clear");
        Button backButton = new Button("Back");

        // set up button actions and adds buttons
        // trims fields and calls handleSubmit()
        submitButton.setOnAction(e -> handleSubmit(
                usernameField.getText().trim(),
                passwordField.getText().trim(),
                emailField.getText().trim(),
                bioField.getText()  // do not trim() bio, it's allowed to have spaces
        ));
        clearButton.setOnAction(e -> handleClear());
        backButton.setOnAction(e -> showMain());

        grid.add(submitButton, 0, 5);
        grid.add(clearButton, 1, 5);
        grid.add(backButton, 2, 5);

        // adds terminal output to the bottom
        grid.add(new Label("Terminal Output:"), 0, 7);
        grid.add(terminalOutputLabel, 1, 7);

        VBox root = new VBox(grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to handle POST with all inputs
    private void handleSubmit(String username, String password, String email, String bio) {
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || bio.isEmpty()) {
            printTerminalOutput("All fields are required.");
        } else {
            handleClear();
            printTerminalOutput(manager.createUser(username, password, email, bio, null));
        }
    }

    // clear all fields
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        bioField.clear();
        printTerminalOutput("Cleared Fields!");
    }

    private void showMain() {
        MainGUI mainGUI = new MainGUI();
        try {
            mainGUI.start(primaryStage); // Reuse the primary stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update the terminal (what the user sees in the GUI)
    private void printTerminalOutput(String newValue) {
        terminalOutput.set(newValue);
    }
}

class LoginGUI extends Application implements SharedResources{

    private Stage primaryStage; // Current GUI being displayed

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label terminalOutputLabel = new Label();
    private StringProperty terminalOutput = new SimpleStringProperty("");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Login");

        // create grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // add labels
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);

        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        // Bind the terminal output label to its output
        terminalOutputLabel.textProperty().bind(terminalOutput);

        // Create buttons
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        // Set up button actions
        submitButton.setOnAction(e -> handleSubmit(
                usernameField.getText().trim(),
                passwordField.getText().trim()
        ));
        backButton.setOnAction(e -> showMain());

        // Add buttons to the grid
        grid.add(submitButton, 0, 2);
        grid.add(backButton, 1, 2);

        // adds terminal output to the bottom
        grid.add(new Label("Terminal Output:"), 0, 7);
        grid.add(terminalOutputLabel, 1, 7);

        // more setup
        VBox root = new VBox(grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSubmit(String username, String password) {
        if (authenticator.authenticate(username, password)) {
            printTerminalOutput("User successfully logged in.");
            showUserProfile(username);
        } else {
            printTerminalOutput("Login failed. Username or password is incorrect.");
        }
    }

    private void showMain() {
        MainGUI mainGUI = new MainGUI();
        try {
            mainGUI.start(primaryStage); // Reuse the primary stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUserProfile(String username) {
        // would redirect to UserGUI
        // to write this GUI and on, likely need to create an interface, where idTracker is static
        // interface -> only purpose is so each GUI has access to necessary data
        // data stored is the manager and authenticator in the interface
        UserGUI userGUI = new UserGUI(primaryStage, username);
        try {
            userGUI.start(primaryStage); // Reuse the primary stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update the terminal (what the user sees in the GUI)
    private void printTerminalOutput(String newValue) {
        terminalOutput.set(newValue);
    }
}

class UserGUI extends Application implements SharedResources{

    private Stage primaryStage; // current GUI being displayed
    private Label terminalOutputLabel = new Label();
    private StringProperty terminalOutput = new SimpleStringProperty("");
    private String username;

    public UserGUI(Stage primaryStage, String username) {
        this.primaryStage = primaryStage;
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        // data that is available
        String userData = manager.getUser(this.username);
        /*** Example Output of userData
         * {"id":4,"username":"test2","email":"test@ourdue.edu","bio":"test","friends":{}}
         */
        System.out.println(userData);
        int id = Integer.parseInt(userData.split("\"id\":")[1].split(",")[0].trim());
        String username = userData.split("\"username\":\"")[1].split("\"")[0];
        String email = userData.split("\"email\":\"")[1].split("\"")[0];
        String bio = userData.split("\"bio\":\"")[1].split("\"")[0];
        System.out.println(manager.idTrackerToString());

        // initialize new Stage here
        this.primaryStage = primaryStage;
        primaryStage.setTitle(username + "'s Profile");

        // create a grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20); // Added horizontal spacing between elements
        grid.setVgap(10); // Added vertical spacing between elements

        // username
        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // bio
        Label bioLabel = new Label(bio);
        bioLabel.setStyle("-fx-font-size: 14px;");

        // email
        Label emailLabel = new Label(email);
        emailLabel.setStyle("-fx-font-size: 14px;");

        // dropdown all Users list and some buttons
        // IDEALLY: this dropdown contains a list of ALL users. can send friend request to anyone
        ComboBox<String> dropdownMenu = new ComboBox<>();
        dropdownMenu.getItems().addAll("User 1", "User 2", "User 3", "See All Users");
        dropdownMenu.setPromptText("Search for User");
        Button sendFriendRequestButton = new Button("Send Friend Request");

        // buttons
        Button editDataButton = new Button("Edit Data");
        Button logOutButton = new Button("Log Out");

        // set actions for dropdowns
        dropdownMenu.setOnAction(e -> {
            if ("Reset".equals(dropdownMenu.getValue())) {
                dropdownMenu.setValue(null); // Reset to show prompt text
            }
        });

        // set actions for buttons
        logOutButton.setOnAction(e -> showLogin());

        // Bind the terminal output label to its output
        terminalOutputLabel.textProperty().bind(terminalOutput);

        // arrange grid
        grid.add(usernameLabel, 0, 0);
        grid.add(bioLabel, 0, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(dropdownMenu, 5, 1);
        grid.add(sendFriendRequestButton, 5, 2);
        grid.add(editDataButton, 8, 1);
        grid.add(logOutButton, 0, 40);
        grid.add(new Label("Terminal Output:"), 0, 42);
        grid.add(terminalOutputLabel, 1, 42);

        // make root
        VBox root = new VBox(grid);
        root.setAlignment(Pos.TOP_LEFT); // Align content to the top left
        root.setPadding(new Insets(20));

        // set Stage
        Scene scene = new Scene(root, 1000, 600); // Reduced scene height for better layout
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLogin() {
        LoginGUI loginGUI = new LoginGUI();
        try {
            loginGUI.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update the terminal (what the user sees in the GUI)
    private void printTerminalOutput(String newValue) {
        terminalOutput.set(newValue);
    }
}


