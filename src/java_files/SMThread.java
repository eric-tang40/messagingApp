package java_files;

public class SMThread implements Runnable {
    private String message;
    private boolean isSending;

    public SMThread(String message, boolean isSending) {
        this.message = message;
        this.isSending = isSending;
    }

    @Override
    public void run() {
        if (isSending) {
            sendMessage();
        } else {
            receiveMessage();
        }
    }

    private void sendMessage() {
        // Code to send the message
        System.out.println("Sending: " + message);
    }

    private void receiveMessage() {
        // Code to receive messages (simulate for demo)
        System.out.println("Receiving messages...");
    }
}
