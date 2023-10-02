package Users;

import Courses.ModelCourseOffered;

import java.util.List;


public interface InstructorModel extends UserModel{

    void setIsInstructorOf(List<ModelCourseOffered> courseInstructed);

    List<ModelCourseOffered> getIsInstructorOf();
}
