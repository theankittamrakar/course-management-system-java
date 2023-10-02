package Users;

import Courses.ModelCourseOffered;

import java.util.List;

public class Instructor implements InstructorModel {
    private String name;
    private String surname;
    private String id;
    private String role = "instructor";
    private List<ModelCourseOffered> isInstructorOf;

    public Instructor(){
    }

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

    public List<ModelCourseOffered> getIsInstructorOf() {
        return isInstructorOf;
    }

    public void setIsInstructorOf(List<ModelCourseOffered> courses) {
        this.isInstructorOf = courses;
    }

    public String getType() {
        return role;
    }

}


