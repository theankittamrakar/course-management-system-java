package Courses;

import Users.Instructor;
import Users.Student;

import java.util.List;
import java.util.Map;

public interface ModelCourseOffered {

    List<Student> getStudentsAllowedToEnroll();

    void setStudentsAllowedToEnroll(List<Student> studentsAllowedToEnroll);

    List<Student> getStudentsEnrolled();

    void setStudentsEnrolled(List<Student> studentsEnrolled);

    Map<EvaluationTypes, Weights> getEvaluationStrategies();

    void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies);

    String getCourseName();

    void setCourseName(String courseName);

    String getCourseID();

    void setCourseID(String courseID);

    Integer getSemester();

    void setSemester(Integer semester);

    List<Instructor> getInstructor();

    void setInstructor(List<Instructor> instructor);

    void addInstructor(Instructor instructor);

    void removeInstructor(Instructor instructor);
}
