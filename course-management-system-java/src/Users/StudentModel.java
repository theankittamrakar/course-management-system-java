package Users;

import Courses.ModelCourseOffered;
import Courses.EvaluationTypes;
import Courses.Marks;

import java.util.List;
import java.util.Map;

public interface StudentModel extends UserModel{
    void setCoursesAllowed(List<ModelCourseOffered> allowedCourses);
    void setCoursesEnrolled(List<ModelCourseOffered> enrolledCourses);
    void setEvaluationEntities(Map<ModelCourseOffered, EvaluationTypes> evaluationEntities);
    void setPerCourseMarks(Map<ModelCourseOffered, Marks> perCourseMarks);

    List<ModelCourseOffered> getCoursesAllowed();
    List<ModelCourseOffered> getCoursesEnrolled();
    Map<ModelCourseOffered, EvaluationTypes> getEvaluationEntities();
    Map<ModelCourseOffered, Marks> getPerCourseMarks();
}
