package Operations;

import Login.UserLogin;
import Login.Login;

import java.io.IOException;
import java.util.Scanner;

public class UserOperations {

    public static void AdminMenu(UserLogin user) throws IOException {
        int option;
        Scanner scan = new Scanner(System.in);
        while (true) {

            System.out.println("\nPlease select one of the following operations: ");
            System.out.println("1. Create New User");
            System.out.println("2. Logout");

            System.out.print("Your Option (1-2) ------> ");
            option = scan.nextInt();

            System.out.println("");
            System.out.println("-----------------------------------------------------");
            AdminOperations admin = new AdminOperations();
            admin.startSystem();
            switch (option) {
                case 1:
                    System.out.println("Please specify the new user's user type:");
                    System.out.println("1. Administrator\n2. Instructor\n3. Student");

                    switch (scan.nextInt()) {
                        case 1:
                            admin.createNewUser("admin");
                            break;
                        case 2:
                            admin.createNewUser("instructor");
                            break;
                        case 3:
                            admin.createNewUser("student");
                            break;
                        default:
                            System.out.println("Option selected is incorrect. Please Try again.");
                            break;
                    }

                    break;

                case 2:
                    Login.logout((user.getAuthenticationToken().getSessionID()));
                    break;

                default:
                    System.out.println("-----Invalid Option. Please try again-----");
                    break;
            }

            System.out.println("-------------------------------------------------------------------------------");
        }
    }

    public static void InstructorMenu(UserLogin user) throws IOException {
        int option;
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\nPlease select one of the following operations: ");

            System.out.println("1. Add mark for a student");
            System.out.println("2. Modify mark for a student");
            System.out.println("3. Calculate final grade for a student");
            System.out.println("4: Print class record");
            System.out.println("5. Logout");

            System.out.print("Your Option (1-5) ------> ");
            option = scan.nextInt();

            System.out.println("");
            System.out.println("-------------------------------------------------------------------------------");
            InstructorOperations inOp = new InstructorOperations();

            switch (option) {
                case 1:
                    inOp.addMark();
                    break;
                case 2:
                    inOp.modifyMark();
                    break;
                case 3:
                    inOp.calcFinalGrade();
                    break;
                case 4:
                    inOp.printClassRecord();
                    break;
                case 5:
                    Login.logout((user.getAuthenticationToken().getSessionID()));
                    break;
                default:
                    System.out.println("\nInvalid Option. Please try again");
                    break;

            }
            System.out.println("----------------------------------------------------");

        }

    }

    public static void StudentMenu(UserLogin user) throws IOException {

        int option;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\nPlease select one of the following operations: ");
            System.out.println("1. Enroll in a Course");
            System.out.println("2. Print Result Slip");

            System.out.println("3. Logout");

            System.out.print("Your Option (1-3) ------> ");
            option = scan.nextInt();

            System.out.println("");
            System.out.println("-------------------------------------------------------------------------------");
            StudentOperations stuOp = new StudentOperations();
            switch (option) {
                case 1:
                    stuOp.enrollInCourse(user);
                    break;

                case 2:
                    stuOp.printStudentRecord(user);
                    break;

                case 3:
                    Login.logout((user.getAuthenticationToken().getSessionID()));
                    break;
                default:
                    System.out.println("\n-----Invalid Option. Please try again-----");
                    break;
            }

            System.out.println("-------------------------------------------------------------------------------");

        }
    }

}
