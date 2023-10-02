package TxtFileReader;

import Courses.EvaluationTypes;
import Courses.Marks;
import Courses.ModelCourseOffered;
import Login.Register;
import Users.Student;
import Users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentReader implements UserReader{

    public Student createSystemUserModel(BufferedReader br, ModelCourseOffered course) {
        Student newStudent;
        Map<ModelCourseOffered, Marks> toInput2 = null;
        try{
            String line = br.readLine();
            if(!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
                newStudent = new Student();
                newStudent.setName(line.split("\t")[0]);
                newStudent.setSurname(line.split("\t")[1]);
                newStudent.setID(line.split("\t")[2]);
                List<ModelCourseOffered> toInput = new ArrayList<ModelCourseOffered>();
                newStudent.setCoursesAllowed(toInput);
                Map<ModelCourseOffered, EvaluationTypes> toInput1 = new HashMap<ModelCourseOffered, EvaluationTypes>();
                List<ModelCourseOffered> enrolled = new ArrayList<ModelCourseOffered>();
                newStudent.setCoursesEnrolled(enrolled);
                newStudent.setEvaluationEntities(toInput1);
                toInput2 = new HashMap<ModelCourseOffered, Marks>();
                newStudent.setPerCourseMarks(toInput2);
                //---------------------------------------------
                Register.getInstance().registerUser(newStudent.getID(), newStudent);
            }
            newStudent = (Student) Register.getInstance().getRegisteredUser(line.split("\t")[2]);
            (newStudent.getCoursesAllowed()).add(course);
            newStudent.getEvaluationEntities().put(course, EvaluationTypes.fromString(line.split("\t")[3]));

            String nType = newStudent.getEvaluationEntities().get(course).getText();


            return newStudent;
        }catch(IOException ioe){
            System.out.println(ioe.getMessage() + "exception thrown at StudentModelCreation");
            return null;
        }
    }
}

