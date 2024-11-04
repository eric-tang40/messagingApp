package java_files;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;
import java.util.stream.*;

// NOTE: ONLY MAKE ONE UserManager OBJECT IN THE PROGRAM
public class UserManager {

    // it's more useful to store username as the key, and access ID number as the value
    HashMap<String, Integer> idTracker;

    public UserManager() {
        if (idTracker == null) {
            idTracker = new HashMap<>();
        }
    }

    public String populateHashMap() {
        try {
            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create a GET request to fetch the user data from the backend
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/messaging/users/"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the response body manually
                String responseBody = response.body();

                // Simple parsing logic to extract ID and Username
                // Step 1: Remove square brackets to isolate individual user objects
                responseBody = responseBody.substring(1, responseBody.length() - 1);

                // Step 2: Split by "},{" to separate individual user objects
                String[] userObjects = responseBody.split("\\},\\{");

                // Step 3: Iterate over each user object
                for (String userObject : userObjects) {
                    // Clean up the object string to ensure it has no curly braces or quotes
                    userObject = userObject.replaceAll("[{}\"]", "");

                    // Split by commas to get key-value pairs
                    String[] keyValuePairs = userObject.split(",");

                    // Variables to hold the ID and username
                    int id = -1;
                    String username = "";

                    // Extract ID and username from the key-value pairs
                    for (String pair : keyValuePairs) {
                        String[] keyValue = pair.split(":");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim();
                            String value = keyValue[1].trim();

                            if (key.equals("id")) {
                                id = Integer.parseInt(value);
                            } else if (key.equals("username")) {
                                username = value;
                            }
                        }
                    }

                    // Add to the HashMap if ID and username were found
                    if (id != -1 && !username.isEmpty()) {
                        idTracker.put(username, id);
                    }
                }

                return "HashMap populated successfully with data from the backend.";
            } else {
                return "Failed to fetch data from the backend. Status code: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An exception occurred while populating the HashMap.";
        }
    }

    // NOTE: users cannot have the same username
    public String createUser(String username, String password, String email, String bio, HashMap<String, ArrayList<String>> friends) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // convert the friends array to a JSON field
            // turns it into a Set of key value pairs, with entry as the key and friend as the value
            // .map() allows us to format the String. .collect() to group together array elements
            String friendsToJson;
            if (friends == null) {
                friendsToJson = "{}";
            } else {
                friendsToJson = friends.entrySet().stream()
                        .map(entry -> "\"" + entry.getKey() + "\": " +
                                entry.getValue().stream()
                                        .map(friend -> "\"" + friend + "\"")
                                        .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "{", "}"));
            }

            String json = String.format(
                    """
                    {
                        \"username\": \"%s\",
                        \"password\": \"%s\",
                        \"email\": \"%s\",
                        \"bio\": \"%s\",
                        \"friends\": %s
                    }
                    """,
                    username, password, email, bio, friendsToJson
            );

            // send POST request and handle the response
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/messaging/users/"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                String responseContent = response.body();
                String idString = responseContent.substring(responseContent.indexOf("\"id\":") + 5, responseContent.indexOf(",", responseContent.indexOf("\"id\":"))).trim();
                int userId = Integer.parseInt(idString);
                idTracker.put(username, userId); // add it to the HashMap
                return "User created successfully: " + response.body();
            } else if (response.statusCode() == 200) {
                return "A user with that username already exists.";
            } else {
                return "Failed to create UserProfile. Status code: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An exception was thrown before the program could complete execution.";
        }
    }

    public void flushDatabase() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            for (Map.Entry<String, Integer> entry : idTracker.entrySet()) {
                Integer userId = entry.getValue();
                URI uri = new URI("http://127.0.0.1:8000/messaging/users/" + userId + "/");

                HttpRequest deleteRequest = HttpRequest.newBuilder()
                        .uri(uri)
                        .DELETE()
                        .header("Content-Type", "application/json")
                        .build();

                HttpResponse<String> response = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 204) {
                    System.out.println("Failed to delete user " + entry.getKey() + ". Status code: " + response.statusCode());
                }
            }

            idTracker.clear();
            System.out.println("Database flushed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUser(String username) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // connect the username to an ID in the Map
            Integer userId = idTracker.get(username);

            // construct the URI
            URI uri = new URI("http://127.0.0.1:8000/messaging/users/" + userId + "/");

            // build GET request
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            // send the GET request and handle the response
            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return "User Data: " + response.body();
            } else if (response.statusCode() == 404) {
                return "User " + username + " could not be found.";
            } else {
                return "Failed to retrieve user. Status code: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // This is a PUT/PATCH request
    // fields that are non-empty will be updated, leave fields empty if don't want to update
    public String editUser(String username, String password, String email, String bio, HashMap<String, ArrayList<String>> friends) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // user data to be inputted
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");

            if (username != null && !username.isEmpty()) {
                jsonBuilder.append("\"username\": \"").append(username).append("\", ");
            }
            if (password != null && !password.isEmpty()) {
                jsonBuilder.append("\"password\": \"").append(password).append("\", ");
            }
            if (email != null && !email.isEmpty()) {
                jsonBuilder.append("\"email\": \"").append(email).append("\", ");
            }
            if (bio != null && !bio.isEmpty()) {
                jsonBuilder.append("\"bio\": \"").append(bio).append("\", ");
            }

            // Convert friends to JSON
            // Same logic as in .createUser()
            if (friends != null && !friends.isEmpty()) {
                String friendsToJson = friends.entrySet().stream()
                        .map(entry -> "\"" + entry.getKey() + "\": " +
                                entry.getValue().stream()
                                        .map(friend -> "\"" + friend + "\"")
                                        .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "{", "}"));

                jsonBuilder.append("\"friends\": ").append(friendsToJson).append(", ");
            }


            if (jsonBuilder.length() > 1) {
                jsonBuilder.setLength(jsonBuilder.length() - 2); // Remove extra comma and space
            }
            jsonBuilder.append("}");
            String json = jsonBuilder.toString();

            // connect the username to an ID in the Map
            Integer userId = idTracker.get(username);

            // construct the URI
            URI uri = new URI("http://127.0.0.1:8000/messaging/users/" + userId + "/");

            // build the PUT request
            HttpRequest putRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // send the PUT request and handle the response
            HttpResponse<String> response = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 204) {
                System.out.println("User updated successfully.");
            } else if (response.statusCode() == 404) {
                System.out.println("User " + username + " could not be found.");
            } else {
                System.out.println("Failed to update user. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    // This DELETES a user PERMANENTLY using its username
    public String deleteUser(String username) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // connect the username to an ID in the Map
            Integer userId = idTracker.get(username);

            // construct the URI
            URI uri = new URI("http://127.0.0.1:8000/messaging/users/" + userId + "/");

            // build DELETE request
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .DELETE()
                    .header("Content-Type", "application/json")
                    .build();

            // send the DELETE request and handle the response
            HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

            if (deleteResponse.statusCode() == 204) {
                idTracker.remove(username); // delete it from the HashMap too
                System.out.println("User " + username + " successfully deleted.");
            } else if (deleteResponse.statusCode() == 404) {
                System.out.println("User " + username + " could not be found.");
            } else {
                System.out.println("Failed to delete user. Status code: " + deleteResponse.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    // mainly for debugging purposes
    public ArrayList<String> idTrackerToString() {
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : idTracker.entrySet()) {
            list.add(entry.getKey() + ": " + entry.getValue());
        }
        return list;
    }

    // outputs the entire database
    /**** Example output:
     "id":1,"username":"Eric","email":"eric@example.com","bio":"I'm eric, HASHMAP
     "id":2,"username":"George","email":"george@example.com","bio":"I'm george", HASHMAP
     "id":3,"username":"Bob","email":"bob@example.com","bio":"I'm bob", HASHMAP
     "id":4,"username":"Ringo","email":"ringo@example.com","bio":"I'm ringo", HASHMAP
     */
    // Note: The HASHMAP's output is at the end, and is different. Examples below.
    /****
     * "friends":{"eric":["hi","hi","good morning"],"gabriel":["goodnight","goodnight","bye"]
     */
    public ArrayList<String> outputDatabase() {
        ArrayList<String> list = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();

            // build the URI
            URI uri = new URI("http://127.0.0.1:8000/messaging/users/");

            // build GET request
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            // send the GET request and handle the response
            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // various operations to modify response to produce optimal result
                String responseContent = response.body();
                responseContent = responseContent.substring(1, responseContent.length() - 1);
                String[] users = responseContent.split("},\\{");
                users[0] = users[0].substring(1, users[0].length() - 1);
                users[users.length - 1] = users[users.length - 1].substring(0, users[users.length - 1].length() - 1);
                list.addAll(Arrays.asList(users));
            } else if (response.statusCode() == 404) {
                System.out.println("Database cannot be found");
            } else {
                System.out.println("Failed to retrieve user. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
