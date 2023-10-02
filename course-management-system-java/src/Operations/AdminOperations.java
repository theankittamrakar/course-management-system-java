package Operations;

import Courses.*;
import Login.Register;
import Users.Admin;
import Users.Instructor;
import Users.Student;
import Users.UserModel;
import TxtFileReader.StudentReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminOperations {

    public boolean startSystem() throws IOException {

        CourseValidation factory = new CourseValidation();

        BufferedReader br = new BufferedReader(new FileReader("note_1.txt"));
        if (factory.createCourseOffering(br) == null)
            return false;


        br = new BufferedReader(new FileReader("note_2.txt"));
        if (factory.createCourseOffering(br) == null)
            return false;

        loadNewStudents();


        loadCourses();


        loadGrades();

        br.close();
        return true;

    }

    public void createNewUser(String type) throws IOException {
        UserModel userToAdd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        switch (type) {

            case "admin":
                userToAdd = new Admin();
                queryBasicUserInfo(userToAdd);
                if (Register.getInstance().checkIfUserHasAlreadyBeenCreated(userToAdd.getID())) {
                    System.out.println("Error: Admin being added is already in system:");

                    UserModel foundUser = Register.getInstance().getRegisteredUser(userToAdd.getID());

                    System.out.println("User in system is:\tID: " + foundUser.getID() + "\tFull Name: "
                            + foundUser.getName() + " " + foundUser.getSurname());
                } else {
                    Register.getInstance().registerUser(userToAdd.getID(), userToAdd);
                    System.out.println("User " + userToAdd.getName() + " " + userToAdd.getSurname() + ", "
                            + userToAdd.getID() + " has been added.");
                }
                break;

            case "student":
                userToAdd = new Student();
                queryBasicUserInfo(userToAdd);
                if (Register.getInstance().checkIfUserHasAlreadyBeenCreated(userToAdd.getID())) {
                    System.out.println("Error: Student being added is already in system:");

                    UserModel foundUser = Register.getInstance().getRegisteredUser(userToAdd.getID());

                    System.out.println("User in system is:\tID: " + foundUser.getID() + "\tFull Name: "
                            + foundUser.getName() + " " + foundUser.getSurname());
                } else {
                    List<ModelCourseOffered> eligibleList = new ArrayList<ModelCourseOffered>();
                    ((Student) userToAdd).setEvaluationEntities(new HashMap<ModelCourseOffered, EvaluationTypes>());
                    ((Student) userToAdd).setCoursesEnrolled(new ArrayList<ModelCourseOffered>());
                    ((Student) userToAdd).setPerCourseMarks(new HashMap<ModelCourseOffered, Marks>());


                    while (true) {
                        System.out.println("Please enter course code of an eligible course for this student:");
                        String courseID = br.readLine();

                        if (Register.getInstance().checkIfCourseHasAlreadyBeenCreated(courseID)) {
                            CourseOffered targetCourse = Register.getInstance().getRegisteredCourse(courseID);
                            targetCourse.getStudentsAllowedToEnroll().add((Student) userToAdd);
                            eligibleList.add(targetCourse);

                            System.out.println("Please choose the student type: (1-4)");
                            System.out.println(
                                    "1. Full Time, For Credit\n2. Full Time, For Audit\n3. Part Time, For Credit\n4. Part Time, For Audit");
                            switch (br.readLine()) {
                                case "1":
                                    ((Student) userToAdd).getEvaluationEntities().put(targetCourse,
                                            EvaluationTypes.FULL_CREDIT);
                                    break;
                                case "2":
                                    ((Student) userToAdd).getEvaluationEntities().put(targetCourse,
                                            EvaluationTypes.FULL_AUDIT);
                                    break;
                                case "3":
                                    ((Student) userToAdd).getEvaluationEntities().put(targetCourse,
                                            EvaluationTypes.PART_CREDIT);
                                    break;
                                case "4":
                                    ((Student) userToAdd).getEvaluationEntities().put(targetCourse,
                                            EvaluationTypes.PART_AUDIT);
                                    break;
                            }


                            String line;
                            FileWriter fw = new FileWriter("new_users_list.txt",true);

                            line = courseID + "\n";
                            fw.write(line);
                            line = userToAdd.getName() + "\t" + userToAdd.getSurname() + "\t" + userToAdd.getID() + "\t" + ((Student) userToAdd).getEvaluationEntities().get(targetCourse).getText() + "\n";
                            fw.write(line);
                            fw.close();

                            Weights courseWeighting = targetCourse.getEvaluationStrategies().get(((Student)userToAdd).getEvaluationEntities().get(targetCourse));
                            courseWeighting.initializeIterator();

                            Marks m = new Marks();
                            while (courseWeighting.hasNext()) {
                                m.addToEvalStrategy(courseWeighting.getNextEntry().getKey(), null);
                            }

                            ((Student) userToAdd).getPerCourseMarks().put(targetCourse, m);

                            System.out.println("Add another eligible course? (Y/N)");

                            if (br.readLine().equals("n"))
                                break;
                        } else
                            System.out.print("Error: Course code entered does not exist. ");
                    }
                    ((Student) userToAdd).setCoursesAllowed(eligibleList);

                    Register.getInstance().registerUser(userToAdd.getID(), userToAdd);

                    System.out.println("User " + userToAdd.getName() + " " + userToAdd.getSurname() + ", "
                            + userToAdd.getID() + " has been added.");
                }
                break;

            case "instructor":
                userToAdd = new Instructor();

                queryBasicUserInfo(userToAdd);

                if (Register.getInstance().checkIfUserHasAlreadyBeenCreated(userToAdd.getID())) {
                    System.out.println("Error: Instructor being added is already in system:");
                    UserModel foundUser = Register.getInstance().getRegisteredUser(userToAdd.getID());
                    System.out.println("User in system is:\tID: " + foundUser.getID() + "\tFull Name: "
                            + foundUser.getName() + " " + foundUser.getSurname());
                } else {
                    List<ModelCourseOffered> courseList = new ArrayList<ModelCourseOffered>();

                    while (true) {
                        System.out.println("Please enter course code for the new instructor:");
                        String courseID = br.readLine();

                        if (Register.getInstance().checkIfCourseHasAlreadyBeenCreated(courseID)) {
                            CourseOffered targetCourse = Register.getInstance().getRegisteredCourse(courseID);
                            targetCourse.addInstructor((Instructor) userToAdd);
                            courseList.add(targetCourse);

                            System.out.println("Add another course? (Y/N");

                            if (br.readLine().equals("n"))
                                break;
                        } else
                            System.out.print("Error: Course code entered does not exist. ");
                    }

                    ((Instructor) userToAdd).setIsInstructorOf(courseList);

                    Register.getInstance().registerUser(userToAdd.getID(), userToAdd);

                    System.out.println("User " + userToAdd.getName() + " " + userToAdd.getSurname() + ", "
                            + userToAdd.getID() + " has been added.");
                }
                break;
        }
    }

    private void queryBasicUserInfo(UserModel user) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the following user details\n");

        System.out.println("User ID: (4-digit code to identify user)");
        user.setID(br.readLine());

        System.out.println("User's Name: (The user's first name)");
        user.setName(br.readLine());

        System.out.println("User's Surname: (The user's last name)");
        user.setSurname(br.readLine());

    }

    private void loadCourses() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("enroll_courses.txt"));
        String line = br.readLine();
        String studentID, courseID;

        while(line!=null) {
            studentID = line.split("\t")[0];
            courseID = line.split("\t")[1];
            Student student = (Student) Register.getInstance().getRegisteredUser(studentID);
            CourseOffered course = Register.getInstance().getRegisteredCourse(courseID);

            if(!student.getPerCourseMarks().containsKey(course)) {
                Weights courseWeighting = course.getEvaluationStrategies().get(student.getEvaluationEntities().get(course));
                courseWeighting.initializeIterator();

                Marks m = new Marks();
                while (courseWeighting.hasNext()) {
                    m.addToEvalStrategy(courseWeighting.getNextEntry().getKey(), null);
                }

                student.getPerCourseMarks().put(course, m);
            }

            List<ModelCourseOffered> courseEnrolled = student.getCoursesEnrolled();
            courseEnrolled.add(course);
            student.setCoursesEnrolled(courseEnrolled);

            List<Student> allStudents = course.getStudentsEnrolled();
            allStudents.add(student);
            course.setStudentsEnrolled(allStudents);

            line = br.readLine();
        }

        br.close();
    }

    private void loadGrades() throws IOException {
        int j = 0;
        int i = 0;
        String studentID, courseID;
        String line;
        for (CourseOffered course : Register.getInstance().getAllCourses()) {
            for (Student student : course.getStudentsAllowedToEnroll()){
                BufferedReader freader = new BufferedReader(new FileReader("SavedGrades.txt"));
                line = freader.readLine();
                while (line != null){
                    if (line.split("\t")[0].equals(student.getID()) && line.split("\t")[1].equals(course.getCourseID())){
                        studentID = line.split("\t")[0];
                        courseID = line.split("\t")[1];

                        Map<ModelCourseOffered, Marks> perCourseMarks = student.getPerCourseMarks();
                        CourseOffered course1 = Register.getInstance().getRegisteredCourse(courseID);
                        Marks a = new Marks();
                        for (i = 2, j = 3; i <= 6; i = i + 2, j = j + 2){
                            if (student.getEvaluationEntities().get(course1).getText().equals("PC") && i == 4){
                                break;
                            }
                            else if (!line.split("\t")[j].equals("null")){
                                a.addToEvalStrategy(line.split("\t")[i], Double.parseDouble(line.split("\t")[j]));
                            }
                            else if (line.split("\t")[j].equals("null")){
                                a.addToEvalStrategy(line.split("\t")[i], null);
                            }
                        }
                        perCourseMarks.put(course,  a);
                        student.setPerCourseMarks(perCourseMarks);
                    }
                    line = freader.readLine();
                }
                freader.close();
            }
        }
    }


    private void loadNewStudents() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File("new_users_list.txt")));
        StudentReader factory = new StudentReader();
        String cid = br.readLine();

        while(cid != null) {
            Register.getInstance().getRegisteredCourse(cid).getStudentsAllowedToEnroll().add(factory.createSystemUserModel(br, Register.getInstance().getRegisteredCourse(cid)));
            cid = br.readLine();
        }

        br.close();
    }
}
