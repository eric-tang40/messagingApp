package java_files;

public class UserMessage implements Message {
    private String userKey;
    private int historyMarker;
    private String content;
    private boolean isLiked;

    // Zero-parameter constructors
    public UserMessage() {
        userKey = null;
        historyMarker = 0;
        content = "InvalidUserMessage";
        isLiked = false;
    }
    // Input strings will be in format of [userKey-historyMarker-content]
    public UserMessage(String s) {
        userKey = s.substring(0, s.indexOf("-"));
        s = s.substring(s.indexOf("-") + 1);
        try {
            historyMarker = Integer.parseInt(s.substring(0, s.indexOf("-")));
        } catch (NumberFormatException e) { // If the 
            userKey = null;
            historyMarker = 0;
            content = "InvalidUserMessage";
            isLiked = false;
        }
        content = s.substring(s.indexOf("-") + 1);
        isLiked = false;
    }

    @Override
    public String getUserKey() {
        return userKey;
    }

    @Override
    public int getHistoryMarker() {
        return historyMarker;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean getIsLiked() {
        return isLiked;
    }

    @Override
    public String toString() {
        return String.format("%s-%d-%s", userKey, historyMarker, content);
    }
}
