package Courses;

import Users.Instructor;
import Users.Student;
import java.util.List;
import java.util.Map;


public class CourseOffered implements ModelCourseOffered {

    String courseName;
    String courseID;
    Integer semester;
    List<Instructor> instructor;
    List<Student> studentsAllowedToEnroll;
    List<Student> studentsEnrolled;
    //	check the comments at the Courses.Weights Class this map should contain at most 4 <key, value> pairs (ergo: <FA,value> <FC, value>
//	<PA, value> <PC, value>)
    Map<EvaluationTypes, Weights> evaluationStrategies;

    public List<Student> getStudentsAllowedToEnroll() {
        return studentsAllowedToEnroll;
    }

    public void setStudentsAllowedToEnroll(List<Student> studentsAllowedToEnroll) {
        this.studentsAllowedToEnroll = studentsAllowedToEnroll;
    }

    public List<Student> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<Student> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public Map<EvaluationTypes, Weights> getEvaluationStrategies() {
        return evaluationStrategies;
    }

    public void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies) {
        this.evaluationStrategies = evaluationStrategies;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public List<Instructor> getInstructor() {
        return instructor;
    }

    public void setInstructor(List<Instructor> instructor) {
        this.instructor = instructor;
    }

    public void addInstructor(Instructor instructor){
        this.instructor.add(instructor);
    }

    public void removeInstructor(Instructor instructor){
        this.instructor.remove(instructor);
    }

    public Double calculateFinalGrade(Student student){
        Student target = null;
        Double finalGrade = null;

        target = student;
        finalGrade = 0D;
        Weights weights = evaluationStrategies.get(target.getEvaluationEntities().get(this));
        Marks marks  = target.getPerCourseMarks().get(this);
        weights.initializeIterator();
        marks.initializeIterator();
        while(weights.hasNext()){
            weights.next();
            marks.next();
            if (marks.getCurrentValue() == null){
                finalGrade = 0.0;
                return finalGrade;
            }
            finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey());

        }

        return finalGrade/100;
    }

}
