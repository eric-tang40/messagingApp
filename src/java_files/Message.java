package java_files;

public interface Message {
    String getUserKey();
    int getHistoryMarker();
    String getContent();
    boolean getIsLiked();
    String toString();
}
