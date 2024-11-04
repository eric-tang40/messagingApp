package java_files;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SMThread implements Runnable {
    private final Queue<UserMessage> messageQueue; // Queue for storing messages to be processed
    private final String userKey; // Unique identifier for the user associated with this thread

    public SMThread(String userKey) {
        this.userKey = userKey;
        this.messageQueue = new LinkedBlockingQueue<>(); // Thread-safe queue for messages
    }

    @Override
    public void run() {
        // Continuously process messages from the queue until interrupted
        while (!Thread.currentThread().isInterrupted()) {
            receiveMessage();
            // Future improvements: Handle additional message processing here
        }
    }

    /**
     * Sends a message by creating a UserMessage and adding it to the message queue.
     * This method is open to future modifications, such as handling different message types.
     *
     * @param historyMarker the history marker of the message
     * @param content       the content of the message
     */
    public void sendMessage(int historyMarker, String content) {
        UserMessage message = new UserMessage(String.format("%s-%d-%s", userKey, historyMarker, content));
        messageQueue.offer(message);
        System.out.println("Message sent: " + message);

        // Future improvement: Add message validation or logging
    }

    /**
     * Receives and processes a message by retrieving it from the queue.
     * Placeholder for future improvements in message processing.
     */
    private void receiveMessage() {
        UserMessage message = messageQueue.poll();
        if (message != null) {
            // Process message (currently, just print the content)
            System.out.println("Received message: " + message.getContent());
            // Future improvement: Customize message handling, e.g., save to database
        }
    }

    // Additional methods to allow further extension of SMThread functionality

    /**
     * Method to retrieve the user key associated with this thread.
     * Can be used to identify the user or perform specific user-related actions.
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * Placeholder method for marking a message as "liked" in future implementations.
     * Opens up the possibility of implementing a like/dislike feature.
     *
     * @param message The message to be marked as liked
     */
    public void likeMessage(UserMessage message) {
        // Currently just a placeholder
        messageQueue.offer(message); // Re-add message with updated "liked" status
        System.out.println("Message liked: " + message.getContent());

        // Future improvement: Track likes, notify other components, or log activity
    }

    /**
     * Placeholder method for additional message processing, like saving or analyzing.
     * This method provides a hook for further processing logic without changing the core structure.
     *
     * @param message The message to process
     */
    protected void processMessage(UserMessage message) {
        // Placeholder for message processing logic
        System.out.println("Processing message: " + message);

        // Future improvement: Implement message analysis, saving, or other handling
    }
}
