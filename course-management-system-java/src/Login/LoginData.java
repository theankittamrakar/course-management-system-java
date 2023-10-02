package Login;

import Users.Admin;
import Users.Instructor;
import Users.Student;

public class LoginData {

    public LoginData() {

    }

    public UserLogin createAuthenticatedUser(AuthenticationToken authenticationToken) {
        switch (authenticationToken.getUserType()) {
            case "admin":
                return createLoggedInAdmin(authenticationToken);
            case "student":
                return createLoggedInStudent(authenticationToken);
            case "instructor":
                return createLoggedInInstructor(authenticationToken);
            default:
                return null;
        }
    }

    public StudentLogin createLoggedInStudent(AuthenticationToken authenticationToken) {
        StudentLogin loggedInUser = new StudentLogin();
        Student queriedUser = (Student) Register.getInstance().getRegisteredUser(authenticationToken.getTokenID());
        loggedInUser.setAuthenticationToken(authenticationToken);
        loggedInUser.setID(queriedUser.getID());
        loggedInUser.setName(queriedUser.getName());
        loggedInUser.setSurname(queriedUser.getSurname());

        Register.getInstance().registerLoggedInUser(authenticationToken.getSessionID(), loggedInUser);
        return loggedInUser;

    }

    public AdminLogin createLoggedInAdmin(AuthenticationToken authenticationToken) {
        AdminLogin loggedInUser = new AdminLogin();
        Admin queriedUser = (Admin) Register.getInstance().getRegisteredUser(authenticationToken.getTokenID());
        loggedInUser.setAuthenticationToken(authenticationToken);
        loggedInUser.setID(queriedUser.getID());
        loggedInUser.setName(queriedUser.getName());
        loggedInUser.setSurname(queriedUser.getSurname());

        Register.getInstance().registerLoggedInUser(authenticationToken.getSessionID(), loggedInUser);
        return loggedInUser;
    }

    public InstructorLogin createLoggedInInstructor(AuthenticationToken authenticationToken) {
        InstructorLogin loggedInUser = new InstructorLogin();
        Instructor queriedUser = (Instructor) Register.getInstance().getRegisteredUser(authenticationToken.getTokenID());
        loggedInUser.setAuthenticationToken(authenticationToken);
        loggedInUser.setID(queriedUser.getID());
        loggedInUser.setName(queriedUser.getName());
        loggedInUser.setSurname(queriedUser.getSurname());

        Register.getInstance().registerLoggedInUser(authenticationToken.getSessionID(), loggedInUser);
        return loggedInUser;
    }
}

