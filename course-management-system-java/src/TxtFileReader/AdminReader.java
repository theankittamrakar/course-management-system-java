package TxtFileReader;

import Courses.ModelCourseOffered;
import Login.Register;
import Users.Admin;

import java.io.BufferedReader;
import java.io.IOException;

public class AdminReader implements UserReader {

    public Admin createSystemUserModel(BufferedReader br, ModelCourseOffered course) {
        Admin newAdmin = new Admin();
        try {
            String line = br.readLine();
            if (!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])) {
                // creates new admin user
                newAdmin.setName(line.split("\t")[0]);
                newAdmin.setSurname(line.split("\t")[1]);
                newAdmin.setID(line.split("\t")[2]);
                Register.getInstance().registerUser(newAdmin.getID(), newAdmin);
            }
            return newAdmin;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

}

