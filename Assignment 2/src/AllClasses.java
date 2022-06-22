import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

interface User{
    void viewLectureMaterial(ArrayList<Slide> listOfSlides , ArrayList<Video> listOfVideos);
    void viewComments(ArrayList<Comment> listOfComments);
    void addComments(Course s, Comment c);
    void logout();
}
class Instructor implements User{
    private String name;
    private String courseName;
    private ArrayList<Slide> listOfSlides= new ArrayList<Slide>();
    private ArrayList<Video> listOfVideos= new ArrayList<Video>();

    public Instructor(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void addLectureSlide(Slide s){
        listOfSlides.add(s);
    }
    public void addLectureVideo(Video v){
        listOfVideos.add(v);
    }
    @Override
    public void viewLectureMaterial(ArrayList<Slide> listOfSlides , ArrayList<Video> listOfVideos){
        for(Slide s: listOfSlides){
            System.out.println("Title: " + s.getTopic());
            String[] content = s.getContentOfSlides();
            for (int i = 0; i < s.getNumberOfSlides(); i++) {
                System.out.println("Content of slide " + (i + 1) + ": " + content[i]);
            }
            System.out.println("Number Of Slides: " + s.getNumberOfSlides());
            System.out.println("Date of upload: " + s.getDayOfUpload());
            System.out.println("Uploaded By: " + s.getInstructor().getName() );
            System.out.println();
        }
        System.out.println();
        for(Video v: listOfVideos){
            System.out.println("Title Of Video: " + v.getTopic());
            System.out.println("Video file: " + v.getFileName());
            System.out.println("Date of upload: " + v.getDayOfUpload());
            System.out.println("Uploaded By: " + v.getInstructor().getName());
            System.out.println();
        }
    }
    @Override
    public void addComments(Course s, Comment c){
        s.getListOfComments().add(c);
    }
    @Override
    public void viewComments(ArrayList<Comment> listOfComments){
        for(Comment c: listOfComments){
            System.out.println(c.getComment() + " - " + c.getName());
            System.out.println(c.getDate());
        }
    }
    public void viewAssignments(ArrayList<Quiz> all_quizzes, ArrayList<Assignment> all_assign){
        int cnt = 0;
        for(Assignment a: all_assign){
            System.out.println("ID: "+ a.getIndex() + " Assignment : " + a.getProblemStatement() + " Max Marks: " + a.getMarks());
            cnt++;
        }
        System.out.println();
        for(Quiz q: all_quizzes){
            System.out.println("ID: "+ q.getIndex() + " Question: " + q.getQuestionQuiz());
            cnt++;
        }
    }
    public void gradeAssessments(ArrayList<Student> listOfStudents, ArrayList<Assessment> listOfAssessments){
        for(Assessment as : listOfStudents.get(0).getListOfAssessments()){
            if(as.getRemoveStatus() == Removed.notRemoved){
                System.out.print("ID: " + listOfStudents.get(0).getListOfAssessments().indexOf(as));
                if(as.getFlg() == 0){
                    System.out.println(" Assignment : " + as.getAssign().getProblemStatement() + " Max Marks: " + as.getAssign().getMarks());
                }
                else if(as.getFlg() == 1){
                    System.out.println(" Question: " + as.getQuiz().getQuestionQuiz());
                }
            }
        }

    }
    public void showOpenAssignments(ArrayList<Student> listOfStudents){
        for(Assessment as : listOfStudents.get(0).getListOfAssessments()){
            if(as.getRemoveStatus() == Removed.notRemoved){
                System.out.print("ID: " + listOfStudents.get(0).getListOfAssessments().indexOf(as));
                if(as.getFlg() == 0){
                    System.out.println(" Assignment : " + as.getAssign().getProblemStatement() + " Max Marks: " + as.getAssign().getMarks());
                }
                else if(as.getFlg() == 1){
                    System.out.println(" Question: " + as.getQuiz().getQuestionQuiz());
                }
            }
        }
    }
    @Override
    public void logout(){
        int flg = 100;
    }

}

class Student implements User{
    private String name;
    private ArrayList<Assessment> listOfAssessments = new ArrayList<Assessment>();

    public Student(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public void viewLectureMaterial(ArrayList<Slide> listOfSlides , ArrayList<Video> listOfVideos){
        for(Slide s: listOfSlides){
            System.out.println("Title: " + s.getTopic());
            String[] content = s.getContentOfSlides();
            for (int i = 0; i < s.getNumberOfSlides(); i++) {
                System.out.println("Content of slide " + (i + 1) + " :" + content[i]);
            }
            System.out.println("Number Of Slides: " + s.getNumberOfSlides());
            System.out.println("Date of upload: " + s.getDayOfUpload());
            System.out.println("Uploaded By: " + s.getInstructor().getName() );
        }
        System.out.println();
        for(Video v: listOfVideos){
            System.out.println("Title Of Video: " + v.getTopic());
            System.out.println("Video file: " + v.getFileName());
            System.out.println("Date of upload: " + v.getDayOfUpload());
            System.out.println("Uploaded By: " + v.getInstructor().getName());
        }
    }
    @Override
    public void viewComments(ArrayList<Comment> listOfComments){
        for(Comment c: listOfComments){
            System.out.println(c.getComment() + " - " + c.getName());
            System.out.println(c.getDate());
        }
    }
    @Override
    public void addComments(Course s, Comment c){
        s.getListOfComments().add(c);
    }
    public void viewAssignments(ArrayList<Quiz> all_quizzes, ArrayList<Assignment> all_assign){
        int cnt = 0;
        for(Assignment a: all_assign){
            System.out.println("ID: "+ a.getIndex() + " Assignment : " + a.getProblemStatement() + " Max Marks: " + a.getMarks());
        }
        System.out.println();
        for(Quiz q: all_quizzes){
            System.out.println("ID: "+ q.getIndex() + " Question: " + q.getQuestionQuiz());
        }
    }
    public int pendingAssignments(){
        int pflg = 0;
        for(Assessment as: this.listOfAssessments){
            if(as.getGrade() == Grade.graded || as.getRemoveStatus() == Removed.removed || as.getPendingStatus() == Pending.notPending){
                continue;
            }
            System.out.print("ID: " + listOfAssessments.indexOf(as));
            if(as.getFlg() == 0){
                System.out.println(" Assignment : " + as.getAssign().getProblemStatement() + " Max Marks: " + as.getAssign().getMarks());
                pflg = 1;
            }
            else if(as.getFlg() == 1){
                System.out.println(" Question: " + as.getQuiz().getQuestionQuiz());
                pflg = 1;
            }
        }
        return pflg;
    }
    public ArrayList<Assessment> getListOfAssessments() {
        return listOfAssessments;
    }
    public void viewGrades() {
        for (Assessment as : this.getListOfAssessments()) {
            if (as.getPendingStatus() == Pending.notPending) {
                if (as.getGrade() == Grade.graded) {
                    System.out.println("Graded submissions: ");
                    if (as.getFlg() == 0) {
                        System.out.println("Submission: " + as.getSubmission());
                        System.out.println("Marks scored: " + as.getMarksObtained());
                        System.out.println("Graded by: " + as.getGradedByName());
                    } else if (as.getFlg() == 1) {
                        System.out.println("Quiz Answer: " + as.getAnswerQuiz());
                        System.out.println("Marks scored: " + as.getMarksObtained());
                        System.out.println("Graded by: " + as.getGradedByName());
                    }
                } else if (as.getGrade() == Grade.notGraded) {
                    System.out.println("Non Graded submissions: ");
                    if (as.getFlg() == 0) {
                        System.out.println("Submission: " + as.getSubmission());
                    } else if (as.getFlg() == 1) {
                        System.out.println("Quiz Answer: " + as.getAnswerQuiz());
                    }

                }
            }
        }
    }
    @Override
    public void logout(){
        int flg = 100;
    }
}
class Course{
    private String courseName;
    private ArrayList<Instructor> listOfInstructors;
    private ArrayList<Student> listOfStudents;
    private ArrayList<Video> listOfVideos;
    private ArrayList<Slide> listOfSlides;
    private ArrayList<Assignment> listOfAssignments;
    private ArrayList<Quiz> listOfQuizzes;
    private ArrayList<Comment> listOfComments = new ArrayList<Comment>();
    public Course(String courseName){
        this.courseName = courseName;
        listOfInstructors = new ArrayList<Instructor>();
        listOfInstructors.add(new Instructor("i0"));
        listOfInstructors.add(new Instructor("i1"));
        listOfStudents = new ArrayList<Student>();
        listOfStudents.add(new Student("s0"));
        listOfStudents.add(new Student("s1"));
        listOfStudents.add(new Student("s2"));
        listOfSlides = new ArrayList<Slide>();
        listOfVideos = new ArrayList<Video>();
        listOfAssignments = new ArrayList<Assignment>();
        listOfQuizzes = new ArrayList<Quiz>();
    }
    public void showInstructors(){
        if(getListOfInstructors() == null){
            System.out.println("List Empty");
            return;
        }
        int cnt = 0;
        for(Instructor i : getListOfInstructors()){
            System.out.println(cnt + " - " + i.getName());
            cnt++;
        }
    }
    public void showStudents(){
        if(getListOfStudents() == null){
            System.out.println("List Empty");
            return;

        }
        int cnt = 0;
        for(Student s : getListOfStudents()){
            System.out.println(cnt + " - " + s.getName());
            cnt++;
        }
    }
    public void addAsAssingnment(int flg, Assignment a){
        for(Student s: getListOfStudents()) {
            s.getListOfAssessments().add(new Assessment(flg, a));
        }

    }
    public void addAsQuiz(int flg, Quiz q){
        for(Student s: getListOfStudents()) {
            s.getListOfAssessments().add(new Assessment(flg, q));
        }

    }

    public ArrayList<Instructor> getListOfInstructors() {
        return listOfInstructors;
    }
    public ArrayList<Student> getListOfStudents(){
        return listOfStudents;
    }
    public ArrayList<Video> getListOfVideos(){
        return this.listOfVideos;
    }
    public ArrayList<Slide> getListOfSlides(){
        return this.listOfSlides;
    }

    public ArrayList<Assignment> getListOfAssignments() {
        return this.listOfAssignments;
    }

    public ArrayList<Quiz> getListOfQuizzes() {
        return this.listOfQuizzes;
    }
    public ArrayList<Comment> getListOfComments(){
        return this.listOfComments;
    }
}
interface Material{
    String getTopic();
    Date getDayOfUpload();
    Instructor getInstructor();
}
class Slide implements Material{
    private String topic;
    private int numberOfSlides;
    private String[] contentOfSlides;
    private Date dayOfUpload;
    private Instructor instructor;
    public Slide(String topic, int numberOfSlides, String[] contentOfSlides, Instructor instructor, Date dayOfUpload) {
        this.topic = topic;
        this.numberOfSlides = numberOfSlides;
        this.contentOfSlides= new String[numberOfSlides];
        this.contentOfSlides = contentOfSlides;
        this.dayOfUpload = dayOfUpload;
        this.instructor = instructor;
    }
    public int getNumberOfSlides() {
        return numberOfSlides;
    }
    @Override
    public String getTopic(){
        return this.topic;
    }
    public String[] getContentOfSlides(){
        return this.contentOfSlides;
    }
    @Override
    public Date getDayOfUpload() {
        return this.dayOfUpload;
    }
    @Override
    public Instructor getInstructor(){
        return this.instructor;
    }
}

class Video implements Material{
    private String topic;
    private String fileName;
    private Date dayOfUpload;
    private Instructor instructor;

    public Video(String topic, String fileName, Instructor instructor, Date dayOfUpload){
        this.topic = topic;
        this.fileName = fileName;
        this.dayOfUpload = dayOfUpload;
        this.instructor = instructor;
    }
    public String getFileName(){
        return this.fileName;
    }
    @Override
    public String getTopic(){
        return this.topic;
    }
    @Override
    public Date getDayOfUpload() {
        return this.dayOfUpload;
    }
    @Override
    public Instructor getInstructor(){
        return this.instructor;
    }
}
class Quiz{
    private String questionQuiz;
    private Instructor instructor;
    private int max_marks = 1;
    private Removed s = Removed.notRemoved;
    private int index;
    public Quiz(String questionQuiz, Instructor instructor, int index){
        this.questionQuiz = questionQuiz;
        this.instructor = instructor;
        this.index = index;
    }

    public String getQuestionQuiz() {
        return questionQuiz;
    }

    public void setAnswerQuiz(String questionQuiz) {
        this.questionQuiz = questionQuiz;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public int getIndex() {
        return index;
    }
    public int getMax_marks() {
        return max_marks;
    }
}
class Assignment{
    private String problemStatement;
    private int marks;
    private Instructor instructor;
    private Removed s = Removed.notRemoved;
    private int index;
    public Assignment(int marks, String problemStatement, Instructor instructor, int index){
        this.marks = marks;
        this.problemStatement = problemStatement;
        this.instructor = instructor;
        this.index = index;
    }

    public int getMarks() {
        return marks;
    }
    public String getProblemStatement(){
        return this.problemStatement;
    }

    public int getIndex() {
        return index;
    }
}

class Assessment{
    // 0 for assignment
    // 1 for quiz
    private int marksObtained = 0;
    private int flg;
    private Pending pendingStatus = Pending.pending;
    private String answerQuiz;
    private String submission;
    private Quiz q;
    private Assignment a;
    private Removed status = Removed.notRemoved;
    private Grade grade = Grade.notGraded;
    private Instructor gradedBy;
    public Assessment(int flg, Quiz q){
        this.flg = flg;
        this.q = q;
    }
    public Assessment(int flg, Assignment a){
        this.flg = flg;
        this.a = a;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Quiz getQuiz() {
        return q;
    }

    public Assignment getAssign() {
        return a;
    }

    public int getFlg() {
        return flg;
    }

    public Grade getGrade() {
        return grade;
    }
    public void setSubmission(String submission){
        this.submission = submission;
    }
    public void setAnswerQuiz(String quiz_ans){
        this.answerQuiz = quiz_ans;
    }

    public String getAnswerQuiz() {
        return answerQuiz;
    }

    public String getSubmission() {
        return submission;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }
    public int getMarksObtained(){
        return this.marksObtained;
    }
    public void setGradedBy(Instructor instructor){
        this.gradedBy = instructor;
    }
    public void setRemoveStatus(Removed r){
        this.status = r;
    }
    public Removed getRemoveStatus(){
        return this.status;
    }

    public Pending getPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(Pending pendingStatus) {
        this.pendingStatus = pendingStatus;
    }
    public String getGradedByName(){
        return this.gradedBy.getName();
    }
}
class Comment{
    private String comment;
    private Date date;
    private String name;
    public Comment(String comment, Date date, String name){
        this.comment = comment;
        this.date = date;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
enum Grade{
    graded, notGraded
}
enum Removed{
    removed, notRemoved;
}
enum Pending{
    notPending, pending
}
class LMS{
    private ArrayList<Course> listOfCourses;
    public LMS(){
        listOfCourses = new ArrayList<Course>();
    }
    public void addCourse(Course c){
        this.listOfCourses.add(c);
    }

    public ArrayList<Course> getListOfCourses() {
        return this.listOfCourses;
    }
}