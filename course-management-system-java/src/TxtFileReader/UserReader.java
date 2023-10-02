package TxtFileReader;

import Courses.ModelCourseOffered;
import Users.UserModel;

import java.io.BufferedReader;

public interface UserReader{
    UserModel createSystemUserModel(BufferedReader br, ModelCourseOffered course);
}
