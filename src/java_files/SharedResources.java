package java_files;

public interface SharedResources {
    UserManager manager = new UserManager(); // Shared instance
    Authenticator authenticator = new Authenticator();
}
