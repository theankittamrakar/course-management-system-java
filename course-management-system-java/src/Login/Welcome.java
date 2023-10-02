package Login;

import Operations.UserOperations;

import java.io.IOException;

public class Welcome {

    public static void screen(UserLogin user) throws IOException {
        switch (user.getAuthenticationToken().getUserType()) {
            case "admin":
                System.out.println("Welcome " + user.getName() + " " + user.getSurname() + ",");
                UserOperations.AdminMenu(user);
                break;

            case "instructor":
                System.out.println("Welcome " + user.getName() + " " + user.getSurname() + ",");
                UserOperations.InstructorMenu(user);
                break;

            case "student":
                System.out.println("Welcome " + user.getName() + " " + user.getSurname() + ",");
                UserOperations.StudentMenu(user);
                break;
        }
    }
}

