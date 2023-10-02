package Courses;

import Login.Register;
import TxtFileReader.InstructorReader;
import TxtFileReader.StudentReader;
import TxtFileReader.UserReader;
import Users.Instructor;
import Users.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseValidation {

    public CourseOffered createCourseOffering(BufferedReader br) {
        // TODO Auto-generated method stub
        try{
            String line = br.readLine();
            CourseOffered course = new CourseOffered();
            if(!Register.getInstance().checkIfCourseHasAlreadyBeenCreated(line.split("\t")[1])){
                course.setCourseName(line.split("\t")[0]);
                course.setCourseID(line.split("\t")[1]);
                course.setSemester(Integer.parseInt(line.split("\t")[2]));
                course.setInstructor(new ArrayList<Instructor>());
                course.setStudentsAllowedToEnroll(new ArrayList<Student>());
                course.setEvaluationStrategies(new HashMap<EvaluationTypes, Weights>());
                course.setStudentsEnrolled(new ArrayList<Student>());
                Register.getInstance().registerCourse(course.getCourseID(), course);
            }
            course = Register.getInstance().getRegisteredCourse(line.split("\t")[1]);
            line = br.readLine();

            UserReader theFactory = new InstructorReader();
            for (int i=0;i<Integer.parseInt(line.split("\t")[2]);i++) {
                course.getInstructor().add((Instructor)theFactory.createSystemUserModel(br, course));
            }
            line = br.readLine();
            theFactory = new StudentReader();
            for (int i=0;i<Integer.parseInt(line.split("\t")[2]);i++) {

                course.getStudentsAllowedToEnroll().add((Student)theFactory.createSystemUserModel(br, course));
            }
            line = br.readLine();
            for(int i=0;i<4;i++) {
                line = br.readLine();
                EvaluationTypes evaluationTypes = EvaluationTypes.fromString(line);
                Weights weights = new Weights();
                line = br.readLine();
                int numberOfEvaluationWeights = Integer.parseInt(line.split("\t")[2]);
                for(int j=0; j<numberOfEvaluationWeights;j++){
                    line = br.readLine();
                    weights.addToEvalStrategy(line.split("\t")[0], Double.parseDouble(line.split("\t")[1]));
                }

                course.getEvaluationStrategies().put(evaluationTypes, weights);
            }
            return course;
        }catch(IOException ioe){
            System.out.println(ioe.getMessage() + "exception thrown at CourseOfferingCreation");
            return null;
        }
    }


}
