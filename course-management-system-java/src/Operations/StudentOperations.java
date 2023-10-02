package Operations;

import Courses.*;
import Login.Register;
import Login.UserLogin;
import Users.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StudentOperations {


    public void enrollInCourse(UserLogin lu) throws IOException {
        if (lu.getAuthenticationToken().getUserType().equals("student")) {
            String courseID;
            int choiceCourse;
            boolean allowed = false;

            Student student = (Student) Register.getInstance().getRegisteredUser(lu.getID());
            Map<Integer, String> choice = new HashMap<Integer, String>();

            while(true) {
                System.out.println("Select the Course ID of the course you would like to enroll in:  ");


                List<ModelCourseOffered> courselist = student.getCoursesAllowed();
                Iterator<ModelCourseOffered> courseIteratorChoice = courselist.iterator();

                Scanner scan = new Scanner(System.in);

                int x = 1;
                String courseChoices;
                while (courseIteratorChoice.hasNext()) {
                    courseChoices = courseIteratorChoice.next().getCourseID();
                    choice.put(x, courseChoices);
                    System.out.println(x + ". " + courseChoices);
                    x++;
                }

                System.out.print("Your Choice (-1 to cancel) ------> ");
                choiceCourse = scan.nextInt();

                if (choiceCourse < x && choiceCourse >=1 || choiceCourse == -1)
                    break;

                System.out.println("\n-----INVALID OPTION-----\n");
            }

            if (choiceCourse != -1) {
                courseID = choice.get(choiceCourse);

                CourseOffered course = Register.getInstance().getRegisteredCourse(courseID);

                List<ModelCourseOffered> courseAllowed = student.getCoursesAllowed();
                Iterator<ModelCourseOffered> courseIterator = courseAllowed.iterator();
                while(courseIterator.hasNext() && allowed == false) {
                    if (courseIterator.next().getCourseID().equals(courseID))
                        allowed = true;
                }

                boolean enrolled = false;


                if (allowed) {
                    List<ModelCourseOffered> courseEnrolled = student.getCoursesEnrolled();
                    Iterator<ModelCourseOffered> courseEnrollIterator = courseEnrolled.iterator();

                    while(courseEnrollIterator.hasNext()) {
                        if(courseEnrollIterator.next().getCourseID().equals(courseID)) {
                            System.out.println("\n-----ERROR: Already Enrolled-----\n");
                            enrolled = true;
                        }
                    }

                    if(!enrolled) {
                        courseEnrolled.add(course);
                        student.setCoursesEnrolled(courseEnrolled);

                        List<Student> allStudents = course.getStudentsEnrolled();

                        if (allStudents.size() < 1000) {

                            if(!student.getPerCourseMarks().containsKey(course)) {
                                Weights courseWeighting = course.getEvaluationStrategies().get(student.getEvaluationEntities().get(course));
                                courseWeighting.initializeIterator();

                                Marks m = new Marks();
                                while (courseWeighting.hasNext()) {
                                    m.addToEvalStrategy(courseWeighting.getNextEntry().getKey(), null);
                                }

                                student.getPerCourseMarks().put(course, m);
                            }

                            allStudents.add(student);
                            course.setStudentsEnrolled(allStudents);

                            String line;
                            line = student.getID() + "\t" + courseID + "\n";

                            FileWriter fw = new FileWriter("enroll_courses.txt",true);
                            fw.write(line);
                            fw.close();

                            System.out.println("\n" + student.getName() + " " + student.getSurname() + " has successfully been enrolled in " + courseID);

                        }
                        else
                            System.out.println("Class is Full");

                    }
                }
            }
        }
        else
            System.out.println("\nYou are not authorized to enroll in a Course");
    }

    public void printStudentRecord(UserLogin lu) {
        if (lu.getAuthenticationToken().getUserType().equals("student")) {
            Scanner scan = new Scanner(System.in);
            int choice;
            String courseID;

            Student student = (Student) Register.getInstance().getRegisteredUser(lu.getID());
            Map<Integer, String> courseOption = new HashMap<Integer, String>();


            while(true) {
                System.out.println("Select a course for which you would like to print your records?");

                List<ModelCourseOffered> coursesEnrolled = student.getCoursesEnrolled();
                Iterator<ModelCourseOffered> coursesEnrolledIterator = coursesEnrolled.iterator();

                int x = 1;
                String courseChoices;
                while (coursesEnrolledIterator.hasNext()) {
                    courseChoices = coursesEnrolledIterator.next().getCourseID();
                    courseOption.put(x, courseChoices);
                    System.out.println(x + ". " + courseChoices);
                    x++;
                }

                if (x==1) {
                    System.out.println("\nERROR: You are not enrolled in any courses!-----");
                    choice = 0;
                    break;
                }

                System.out.print("Your Choice (-1 to cancel)------> ");
                choice = scan.nextInt();

                if (choice < x && choice >=1 || choice == -1)
                    break;

                System.out.println("\nINVALID CHOICE!\n");
            }


            if (choice != -1) {
                courseID = courseOption.get(choice);

                boolean enrolled = false;

                List<ModelCourseOffered> courseEnrolled = student.getCoursesEnrolled();
                Iterator<ModelCourseOffered> courseIterator = courseEnrolled.iterator();

                while(courseIterator.hasNext() && enrolled == false) {
                    if (courseIterator.next().getCourseID().equals(courseID))
                        enrolled = true;
                }

                if(enrolled) {
                    Map<ModelCourseOffered, EvaluationTypes> allEval = student.getEvaluationEntities();
                    CourseOffered course = Register.getInstance().getRegisteredCourse(courseID);
                    EvaluationTypes eval = allEval.get(course);

                    System.out.println("Course Name: " + course.getCourseName());
                    System.out.println("Course ID: " + courseID + "\n");
                    Map<EvaluationTypes, Weights> allWeights = course.getEvaluationStrategies();
                    Weights weight = allWeights.get(eval);

                    weight.initializeIterator();
                    Map.Entry<String,Double> type = weight.getNextEntry();
                    System.out.println("Your Evaluation Type: " + eval.getText());
                    System.out.println("Your Evaluation Entities: ");
                    while(type!=null) {
                        System.out.print(type.getKey() + "--> ");
                        System.out.println(type.getValue());
                        type = weight.getNextEntry();
                    }

                    Map<ModelCourseOffered, Marks> allMark = student.getPerCourseMarks();
                    Marks marks = allMark.get(course);

                    marks.initializeIterator();
                    Map.Entry<String, Double> yourMarks = marks.getNextEntry();
                    System.out.println("\nYour Marks: ");
                    while(yourMarks != null) {
                        System.out.print(yourMarks.getKey() + "--> ");

                        if(yourMarks.getValue() == null)
                            System.out.println("--");
                        else
                            System.out.println(yourMarks.getValue());

                        yourMarks = marks.getNextEntry();
                    }
                }
            }
        }
    }

}

