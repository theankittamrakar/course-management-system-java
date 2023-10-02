package Users;

import Courses.ModelCourseOffered;
import Courses.EvaluationTypes;
import Courses.Marks;

import java.util.List;
import java.util.Map;

public class Student implements StudentModel{
    private String name;
    private String surname;
    private String id;
    private String role = "student";
    private List<ModelCourseOffered> coursesAllowed;
    private List<ModelCourseOffered> coursesEnrolled;
    private Map<ModelCourseOffered, EvaluationTypes> evaluationEntities;
    private Map<ModelCourseOffered, Marks> perCourseMarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        id = ID;
    }

    public List<ModelCourseOffered> getCoursesAllowed() {
        return coursesAllowed;
    }

    public void setCoursesAllowed(List<ModelCourseOffered> coursesAllowed) {
        this.coursesAllowed = coursesAllowed;
    }

    public List<ModelCourseOffered> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(List<ModelCourseOffered> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    public Map<ModelCourseOffered, EvaluationTypes> getEvaluationEntities() {
        return evaluationEntities;
    }

    public void setEvaluationEntities(Map<ModelCourseOffered, EvaluationTypes> evaluationEntities) {
        this.evaluationEntities = evaluationEntities;
    }

    public Map<ModelCourseOffered, Marks> getPerCourseMarks() {
        return perCourseMarks;
    }

    public void setPerCourseMarks(Map<ModelCourseOffered, Marks> perCourseMarks) {
        this.perCourseMarks = perCourseMarks;
    }

    public String getType() {
        return role;
    }


}

