package Login;

import TxtFileReader.AdminReader;
import Users.UserModel;

import java.io.*;

public class Login {
    public static void initialize(String usersDB) {

        try {

            BufferedReader br = new BufferedReader(new FileReader(new File(usersDB)));
            AdminReader AdminFact = new AdminReader();

            String line = br.readLine();

            for (int i = 0; i < Integer.parseInt(line.split("\t")[1]); i++) {
                AdminFact.createSystemUserModel(br, null);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void interact() throws IOException {

        System.out.println("Welcome To Course Management System");
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        String id;
        System.out.println("Please Provide Your Login ID:");
        while (true) {

            id = scan.readLine();

            if (!Register.getInstance().checkIfUserHasAlreadyBeenCreated(id)) {
                System.out.println("Error: User ID not correct. Please try again with a valid ID:");
                continue;
            } else {
                break;
            }
        }


        AuthenticationToken certifiedToken = authenticate(id);

        if (certifiedToken != null) {
            LoginData loginFactory = new LoginData();
            UserLogin user = loginFactory.createAuthenticatedUser(certifiedToken);

            Welcome.screen(user);
        }
    }


    public static AuthenticationToken authenticate(String userID) {

        if (!Register.getInstance().checkIfUserHasAlreadyBeenCreated(userID)) {
            System.out.println("Error: Login Failed, System is offline");
            return null;
        } else {
            UserModel loginUser = Register.getInstance().getRegisteredUser(userID);
            return generateToken(loginUser);
        }

    }

    private static AuthenticationToken generateToken(UserModel user) {
        AuthenticationToken userToken = new AuthenticationToken();
        userToken.setUserType(user.getType());
        userToken.setTokenID(user.getID());
        int sessionCode = LoginSession.createSession();
        if (userToken.getUserType().equals("admin"))
            sessionCode += 1000;
        else if (userToken.getUserType().equals("instructor"))
            sessionCode += 2000;
        else
            sessionCode += 3000;

        userToken.setSessionID(sessionCode);

        return userToken;

    }

    public static void logout(Integer sessionID) throws IOException {

        UserLogin user = Register.getInstance().getLoggedInUser(sessionID);

        Register.getInstance().LogOutUser(sessionID);

        interact();

    }

}

