package Login;

public class LoginSession {

    private static int sessionTracker;	// using a persistent counter to keep track the users logged in during a server life time

    public static void initialize() {
        sessionTracker = 0;
    }

    public static void reset() {
        sessionTracker = -1;
    }

    public static int createSession() {
        sessionTracker++;
        return sessionTracker;
    }

    public static int sessionsInitialized() {
        return sessionTracker;
    }
}

