package Login;

import Courses.CourseOffered;
import Users.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    private Map<String, UserModel> modelRegister = new HashMap<String, UserModel>();

    private Map<String, CourseOffered> courseRegister = new HashMap<String, CourseOffered>();
    private Map<Integer, UserLogin> loggedInRegister = new HashMap<Integer, UserLogin>();


    private static Register instance;

    private Register() {
    }

    public static Register getInstance() {
        if (instance == null)
            instance = new Register();
        return instance;
    }

    public boolean checkIfUserHasAlreadyBeenCreated(String ID) {
        return modelRegister.containsKey(ID);
    }

    public void registerUser(String ID, UserModel user) {
        modelRegister.put(ID, user);
    }

    public UserModel getRegisteredUser(String ID) {
        return modelRegister.get(ID);
    }

    public boolean checkIfCourseHasAlreadyBeenCreated(String ID) {
        return courseRegister.containsKey(ID);
    }

    public void registerCourse(String ID, CourseOffered course) {
        courseRegister.put(ID, course);
    }

    public CourseOffered getRegisteredCourse(String ID) {
        return courseRegister.get(ID);
    }

    public List<CourseOffered> getAllCourses() {
        List<CourseOffered> courses = new ArrayList<CourseOffered>();
        courses.addAll(courseRegister.values());
        return courses;
    }

    public void registerLoggedInUser(Integer ID, UserLogin lu) {
        loggedInRegister.put(ID, lu);
    }

    public void LogOutUser(Integer ID) {
        loggedInRegister.remove(ID);
    }

    public UserLogin getLoggedInUser(Integer ID) {
        return loggedInRegister.get(ID);
    }

    public boolean isActiveUser(Integer ID) {
        return loggedInRegister.containsKey(ID);
    }

    public void clearUserLogin() {
        loggedInRegister.clear();
    }

}

