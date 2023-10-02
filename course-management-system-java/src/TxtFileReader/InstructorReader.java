package TxtFileReader;

import Courses.ModelCourseOffered;
import Login.Register;
import Operations.UserOperations;
import Users.Instructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class InstructorReader implements UserReader {

    public Instructor createSystemUserModel(BufferedReader br, ModelCourseOffered course) {
        Instructor newInstructorModel = new Instructor();
        try{
            String line = br.readLine();
            if(!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
                newInstructorModel.setName(line.split("\t")[0]);
                newInstructorModel.setSurname(line.split("\t")[1]);
                newInstructorModel.setID(line.split("\t")[2]);
                newInstructorModel.setIsInstructorOf(new ArrayList<ModelCourseOffered>());
                Register.getInstance().registerUser(newInstructorModel.getID(), newInstructorModel);
            }
            newInstructorModel = (Instructor) Register.getInstance().getRegisteredUser(line.split("\t")[2]);
            newInstructorModel.getIsInstructorOf().add(course);
            return newInstructorModel;
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
            return null;
        }
    }

}

