import java.util.Date;
import java.util.Scanner;
import java.util.*;
public class Main {
    public static void showScreen(){
        System.out.println("Welcome to Backpack\n 1. Enter as instructor\n 2. Enter as student\n 3. Exit");
    }
    public static void showInstructorMenu(){
        System.out.println("INSTRUCTOR MENU\n 1. Add class material\n 2. Add assessments\n 3. View lecture materials\n 4. View assessments\n 5. Grade assessments\n 6. Close assessment\n 7. View comments\n 8. Add comments\n 9. Logout\n");
    }
    public static void showStudentMenu(){
        System.out.println("STUDENT MENU\n 1. View lecture materials\n 2. View assessments\n 3. Submit assessment\n 4. View grades\n 5. View comments\n 6. Add comments\n 7. Logout\n");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scc = new Scanner(System.in);
        int index = 0;
        LMS lms = new LMS();
        Course system = new Course("Course Name");
        lms.addCourse(system);
        int choice = 0;
        int instructorChoice;
        int studentChoice;
        int loopcnt;
        do
        {
            showScreen();
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    system.showInstructors();
                    System.out.print("Choose ID: ");
                    int instructorID = sc.nextInt();
                    Instructor instructor = system.getListOfInstructors().get(instructorID);
                    int flginstructor = 1;

                    while(flginstructor == 1){
                        System.out.println();
                        System.out.println("Welcome " + instructor.getName());
                        showInstructorMenu();
                        instructorChoice = sc.nextInt();
                        switch (instructorChoice) {
                            case 1:
                                System.out.println("1. Add Lecture Slide\n2. Add Lecture Video\n");
                                int materialChoice = sc.nextInt();
                                if (materialChoice == 1) {

                                    System.out.println("Enter topic of slides: ");
                                    sc.nextLine();
                                    String topic = sc.nextLine();
                                    System.out.println("Enter number of slides:");
                                    int noofslides = sc.nextInt();
                                    String[] content = new String[noofslides];
                                    sc.nextLine();
                                    for (int i = 0; i < noofslides; i++) {
                                        System.out.println("Content of slide " + (i + 1) + " :");
                                        content[i] = sc.nextLine();
                                    }
                                    Date dateOfUpload = new Date();

                                    Slide s = new Slide(topic, noofslides, content, instructor, dateOfUpload);
                                    system.getListOfSlides().add(s);
                                    instructor.addLectureSlide(s);
                                } else if (materialChoice == 2) {
                                    System.out.println("Enter topic of video: ");
                                    sc.nextLine();
                                    String topicvid = sc.nextLine();
                                    System.out.println("Enter filename of video: ");
                                    String filename = sc.nextLine();
                                    String[] tokens = filename.split("\\.");
                                    if (tokens[tokens.length - 1].equals("mp4")) {
                                    } else {
                                        System.out.println("Format of Video not supported, try again.");
                                        break;
                                    }
                                    Date dateOfUpload = new Date();

                                    Video v = new Video(topicvid, filename, instructor, dateOfUpload);
                                    system.getListOfVideos().add(v);
                                    instructor.addLectureVideo(v);
                                } else {
                                    System.out.println("Wrong choice selected, select either 1 or 2");
                                }
                                break;
                            case 2:
                                System.out.println("1. Add Assignment\n" + "2. Add Quiz");
                                int assingmentChoice = sc.nextInt();
                                if(assingmentChoice == 1){
                                    System.out.println("Enter problem statement: ");
                                    sc.nextLine();
                                    String ps = sc.nextLine();
                                    System.out.println("Enter max marks:" );
                                    int marks = sc.nextInt();
                                    Assignment a = new Assignment(marks, ps, instructor, index);
                                    system.getListOfAssignments().add(a);
                                    system.addAsAssingnment(0, a);
                                    index++;
                                }
                                else if(assingmentChoice == 2){
                                    System.out.println("Enter quiz question: ");
                                    sc.nextLine();
                                    String quiz_question = sc.nextLine();
                                    Quiz qq = new Quiz(quiz_question, instructor, index);
                                    system.getListOfQuizzes().add(qq);
                                    system.addAsQuiz(1, qq);
                                    index++;
                                }
                                else{
                                    System.out.println("Wrong choice selected, select either 1 or 2");
                                }
                                break;
                            case 3:
                                instructor.viewLectureMaterial(system.getListOfSlides(), system.getListOfVideos());
                                break;
                            case 4:
                                instructor.viewAssignments(system.getListOfQuizzes(), system.getListOfAssignments());
                                break;
                            case 5:
                                instructor.viewAssignments(system.getListOfQuizzes(), system.getListOfAssignments());
                                System.out.println("Enter ID of assessment to view submissions: ");
                                int id_assessment = sc.nextInt();
                                int sysflg = 0;
                                System.out.println("Choose ID from these ungraded submissions: ");
                                for(Student s: system.getListOfStudents()){
                                    if (s.getListOfAssessments().get(id_assessment).getGrade() == Grade.notGraded &&  s.getListOfAssessments().get(id_assessment).getPendingStatus() == Pending.notPending){
                                        System.out.println(system.getListOfStudents().indexOf(s) + " " + s.getName());
                                            sysflg = 1;
                                        } // TODO show empty, if no student selected
                                          // TODO if a student not submitted a assignment no need to grade it. Done
                                }         // TODO can be graded even after closing
                                          // TODO s.getListOfAssessments().get(id_assessment).getRemoveStatus() == Removed.notRemoved &&
                                if(sysflg == 0){
                                    System.out.println("No Students to display.");
                                    break;
                                }
                                int student_id = sc.nextInt();
                                if(system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).getFlg() == 0){
                                    System.out.println("Submission: "+ system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).getSubmission());
                                    System.out.println("Max Marks: "+ system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).getAssign().getMarks());
                                    System.out.println("Marks Scored: ");
                                    int marksObtained = sc.nextInt();
                                    system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).setMarksObtained(marksObtained);
                                    system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).setGradedBy(instructor);
                                    system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).setGrade(Grade.graded);
                                }
                                else {
                                    System.out.println(system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).getAnswerQuiz());
                                    System.out.println("Max Marks: "+ system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).getQuiz().getMax_marks());
                                    System.out.println("Marks Scored: ");
                                    int marksObtained = sc.nextInt();
                                    system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).setMarksObtained(marksObtained);
                                    system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).setGradedBy(instructor);
                                    system.getListOfStudents().get(student_id).getListOfAssessments().get(id_assessment).setGrade(Grade.graded);
                                }
                                break;
                            case 6:
                                System.out.println("List Of Open Assignments: ");
                                instructor.showOpenAssignments(system.getListOfStudents());
                                System.out.println("Enter id of assignment to close: ");
                                int id_a = sc.nextInt();
                                for(Student s: system.getListOfStudents()){
                                    s.getListOfAssessments().get(id_a).setRemoveStatus(Removed.removed);
                                }
                                break;
                            case 7:
                                instructor.viewComments(system.getListOfComments());
                                break;
                            case 8:
                                Date datacomment = new Date();
                                System.out.println("Enter Comment");
                                sc.nextLine();
                                String comment = sc.nextLine();
                                Comment c = new Comment(comment,datacomment ,instructor.getName());
                                instructor.addComments(system, c);
                                break;
                            case 9:
                                flginstructor = 0;
                                instructor.logout();
                                break;
                            default:
                                System.out.println("Enter Correct Choice");
                        }

                    }
                    break;
                case 2:
                    system.showStudents();
                    System.out.print("Choose ID: ");
                    int studentID = sc.nextInt();
                    Student student = system.getListOfStudents().get(studentID);
                    int studentflg = 1;
                    while(studentflg == 1) {
                        System.out.println();
                        System.out.println("Welcome " + student.getName());
                        showStudentMenu();
                        studentChoice = sc.nextInt();
                        switch (studentChoice){
                            case 1:
                                student.viewLectureMaterial(system.getListOfSlides(), system.getListOfVideos());
                                break;
                            case 2:
                                student.viewAssignments(system.getListOfQuizzes(), system.getListOfAssignments());
                                break;
                            case 3:
                                System.out.println("Pending Assignments: ");
                                int pflg = student.pendingAssignments();
                                // pflg == 1 , pending assignments
                                // pflg == 0, no pending assignments
                                if(pflg == 0){
                                    System.out.println("No Pending Assessments");
                                    break;
                                }
                                // TODO if no pending assignment, then no id is asked.
                                System.out.println("Enter ID of assessment: ");
                                int choiceAs = sc.nextInt();
                                int flg1 = student.getListOfAssessments().get(choiceAs).getFlg();
                                if(flg1 == 0){
                                    System.out.println("Enter filename of assignment: ");
                                    sc.nextLine();
                                    String fname = sc.nextLine();
                                    String[] tokens = fname.split("\\.");
                                    if (tokens[tokens.length - 1].equals("zip")) {
                                    }
                                    else {
                                        System.out.println("Format of Uploaded file not supported, try again.");
                                        break;
                                    }

                                    student.getListOfAssessments().get(choiceAs).setSubmission(fname);
                                     student.getListOfAssessments().get(choiceAs).setPendingStatus(Pending.notPending);
                                }
                                else if(flg1 == 1){
                                    System.out.println(student.getListOfAssessments().get(choiceAs).getQuiz().getQuestionQuiz() + " : ");
                                    sc.nextLine();
                                    String answer = sc.nextLine();
                                    student.getListOfAssessments().get(choiceAs).setAnswerQuiz(answer);
                                    student.getListOfAssessments().get(choiceAs).setPendingStatus(Pending.notPending);
                                }
                                break;
                            case 4:
                                student.viewGrades();
                                break;
                            case 5:
                                student.viewComments(system.getListOfComments());
                                break;
                            case 6:
                                Date data_commet = new Date();
                                System.out.println("Enter Comment: ");
                                sc.nextLine();
                                String comment = sc.nextLine();
                                Comment c = new Comment(comment,data_commet ,student.getName());
                                student.addComments(system, c);
                                break;
                            case 7:
                                studentflg = 0;
                                break;
                            default:
                                System.out.println("Enter Correct Choice");
                        }

                    }
                    break;
                case 3:
                    System.out.println("Exiting the System");
                    loopcnt = 100;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect Choice.");
            }
        }while(choice!=100);

    }
}
