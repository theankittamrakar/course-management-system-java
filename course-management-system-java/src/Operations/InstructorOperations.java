package Operations;


import Courses.CourseOffered;
import Courses.Marks;
import Courses.ModelCourseOffered;
import Login.Register;
import Users.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Scanner;

public class InstructorOperations extends Marks {

    private String examOrAss, c = "", n = "", s = "", iD = "";
    private Double grade;
    private int input;

    public void addMark() throws IOException {

        Scanner scan = new Scanner(System.in);

        System.out.println("Course: ");
        c = scan.next();
        for(CourseOffered course : Register.getInstance().getAllCourses()){
            if (course.getCourseID().equals(c)){
                System.out.println("Student iD: ");
                iD = scan.next();
                for(Student student : course.getStudentsAllowedToEnroll()){
                    if (student.getID().equals(iD)){
                        n = student.getName();
                        s = student.getSurname();
                        Marks j = student.getPerCourseMarks().get(course);
                        j.initializeIterator();
                        j.next();
                        String a1 = j.getCurrentKey();
                        j.next();
                        String a2 = j.getCurrentKey();
                        j.next();
                        String a3 = j.getCurrentKey();
                        System.out.println("Please enter: \n1 for " + a2 + " \n2 for " + a1 + " \n3 for " + a3);
                        input = scan.nextInt();
                        if (input == 1){
                            examOrAss = a2;
                        }
                        else if (input == 2){
                            examOrAss = a1;
                        }
                        else if (input == 3){
                            examOrAss = a3;
                        }
                        else{
                            System.out.println("Invalid Assessment");
                        }
                        if (!gradeExists(student, course)){

                            System.out.println("Mark: ");

                            grade = (double) scan.nextInt();
                            if (grade > 100.0 || grade < 0.0){
                                System.out.println("Please enter a valid grade!");
                                return;
                            }
                            saveMark(student, course);
                            return;
                        }
                        else{
                            System.out.println("Mark already exists for " + examOrAss + "\nPlease return to main menu and choose Modify Mark");
                            return;
                        }
                    }
                }
                System.out.println("Student Does not exist, please enter a valid student ID");
                return;
            }
        }
        System.out.println("Course Does Not Exist, please enter a valid course ID");
    }

    public void modifyMark() throws IOException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Course: ");
        c = scan.next();
        for(CourseOffered course : Register.getInstance().getAllCourses()){
            if (course.getCourseID().equals(c)){
                System.out.println("Student iD: ");
                iD = scan.next();
                for(Student student : course.getStudentsAllowedToEnroll()){
                    if (student.getID().equals(iD)){
                        n = student.getName();
                        s = student.getSurname();
                        Marks j = student.getPerCourseMarks().get(course);
                        j.initializeIterator();
                        j.next();
                        String a1 = j.getCurrentKey();
                        j.next();
                        String a2 = j.getCurrentKey();
                        j.next();
                        String a3 = j.getCurrentKey();
                        System.out.println("Please enter: \n1 for " + a2 + " \n2 for " + a1 + " \n3 for " + a3);
                        input = scan.nextInt();
                        if (input == 1){
                            examOrAss = a2;
                        }
                        else if (input == 2){
                            examOrAss = a1;
                        }
                        else if (input == 3){
                            examOrAss = a3;
                        }
                        else{
                            System.out.println("Invalid Assessment");
                        }
                        if (gradeExists(student, course)){

                            System.out.println("Mark: ");
                            grade = (double) scan.nextInt();
                            if (grade > 100.0 || grade < 0.0){
                                System.out.println("Please enter a valid grade!");
                                return;
                            }
                            saveMark(student, course);
                            return;
                        }
                        else{
                            System.out.println("Mark does not exist for " + examOrAss + "\nPlease return to main menu and choose Add Mark");
                            return;
                        }
                    }
                }
                System.out.println("Student Does not exist, please enter a valid student ID");
                return;
            }
        }
        System.out.println("Course Does Not Exist, please enter a valid course ID");
    }

    public void calcFinalGrade() throws IOException{

        Scanner scan = new Scanner(System.in);

        System.out.println("Course: ");
        c = scan.next();
        for(CourseOffered course : Register.getInstance().getAllCourses()){

            if (course.getCourseID().equals(c)){
                System.out.println("Student iD: ");
                iD = scan.next();

                for(Student student : course.getStudentsAllowedToEnroll()){
                    if (student.getID().equals(iD)){
                        Double result = course.calculateFinalGrade(student);
                        if (result == 0.0){
                            System.out.println("Student is missing evaluations! "
                                    + "\nPlease finish adding grades for student before calculating final grade.");
                        }
                        else{
                            System.out.println("Final Grade Calculated: " + result + "%");
                            return;
                        }
                    }
                }
            }
        }
    }

    private void saveMark(Student student, CourseOffered course) throws IOException{

        Marks a = student.getPerCourseMarks().get(course);
        Double old = null;

        a.initializeIterator();
        while (a.hasNext()){
            a.next();
            if (a.getCurrentKey().equals(examOrAss)){
                old = a.getCurrentValue();
                a.addToEvalStrategy(examOrAss, grade);
                student.getPerCourseMarks().put(course, a);
                if (old == null)
                {
                    System.out.println("For " + a.getCurrentKey() + ", grade changed from - to " + a.getCurrentValue() + "%");
                }
                else{
                    System.out.println("For " + a.getCurrentKey() + ", grade changed from " + old + "% to " + a.getCurrentValue() + "%");
                }
                saveToDB(student, course);

            }
        }
    }


    private boolean gradeExists(Student student, CourseOffered course){
        Marks a = student.getPerCourseMarks().get(course);

        a.initializeIterator();
        while (a.hasNext()){
            a.next();
            if (a.getCurrentKey().equals(examOrAss)){
                if(a.getCurrentValue() != null){

                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("resource")
    private void saveToDB(Student student, CourseOffered course ) throws IOException{

        Writer output = new BufferedWriter(new FileWriter("SavedGrades.txt"));

        int m = 0;
        int n = 0;
        n = 0;
        output = new BufferedWriter(new FileWriter("SavedGrades.txt"));
        for (CourseOffered course1 : Register.getInstance().getAllCourses()) {

            for (Student student1 : course1.getStudentsAllowedToEnroll()){
                m = 0;
                n = 0;
                output.write(student1.getID() + "\t");
                output.write(course1.getCourseID() + "\t");
                Map<ModelCourseOffered, Marks> allMark = student1.getPerCourseMarks();
                Marks j = allMark.get(course1);
                j.initializeIterator();
                while (j.hasNext()){
                    j.next();
                    output.write(j.getCurrentKey() + "\t");
                    output.write(j.getCurrentValue() + "\t");
                }
                output.write("\n");
            }
        }
        output.close();
    }

    public void printClassRecord() {
        for (CourseOffered course : Register.getInstance().getAllCourses()) {
            System.out.println("ID : " + course.getCourseID() + "\nCourse name : " + course.getCourseName()
                    + "\nSemester : " + course.getSemester());
            System.out.println("Students allowed to enroll\n");
            for (Student student : course.getStudentsAllowedToEnroll()) {
                System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname()
                        + "\nStudent ID : " + student.getID() + "\nStudent EvaluationType : "
                        + student.getEvaluationEntities().get(course) + "\n\n");
            }

            for (Student student : course.getStudentsAllowedToEnroll()) {
                for (ModelCourseOffered course2 : student.getCoursesAllowed())
                    System.out.println(student.getName() + "\t\t -> " + course2.getCourseName());
            }
        }
    }

}

