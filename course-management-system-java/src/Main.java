import java.io.IOException;
 import Login.Login;
 import Login.LoginSession;

public class Main {
    public static void main(String[] args)throws IOException{
        Login.initialize("admin_user_list.txt");
        LoginSession.initialize();
        Login.interact();
    }
}
