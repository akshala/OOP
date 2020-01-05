import java.util.ArrayList;
import java.util.Scanner;

class Company{
    private String name;
    private int num_courses;
    private String[] courses;
    private int num_req_students;
    private int[] selected_students;
    private boolean application_status = true;

    Company(String name, int num_courses, String[] courses, int num_req_students){
        this.name = name;
        this.num_courses = num_courses;
        this.courses = courses;
        this.num_req_students = num_req_students;
    }

    void display(){
//        displays information about the company
        System.out.println(name);
        System.out.println("Course Criteria");
        for(int i=0; i<num_courses; i++){
            System.out.println(courses[i]);
        }
        System.out.println("Number of Required Students = " + num_req_students);
        if(application_status){
            System.out.println("Application Status = OPEN");
        }
        else{
            System.out.println("Application Status = CLOSED");
        }
    }

    void close_application(){
        application_status = false;
    }

    int getNum_req_students(){
        return num_req_students;
    }

    boolean name_match(String input_name){
        return input_name.equals(name);
    }

    boolean get_status(){
        return application_status;
    }

    String get_name(){
        return name;
    }

    static class candidates{
//        students eligible to apply for the company
        Student student;
        private int marks;
        candidates(Student student, int marks){
            this.student = student;
            this.marks = marks;
        }

        int get_marks(){
            return marks;
        }

    }

    ArrayList<candidates> possible_candidates = new ArrayList<candidates>();

    void add_student(Student student, int marks){
//        addition of an eligible student
        candidates new_possibility = new candidates(student, marks);
        possible_candidates.add(new_possibility);
    }

    ArrayList<candidates> top_k(Student sample, int k){
//        getting the students that have been shortlisted
        ArrayList<candidates> selected = new ArrayList<candidates>();
        if(k > possible_candidates.size()){
//            if number to be selected is more than the number of eligible students
            k = possible_candidates.size();
        }
        for(int i=0; i<k; i++){
            candidates max = new candidates(sample, 0);
            int max_marks = 0;
            for (Company.candidates choice : possible_candidates){
//                selecting top k
                if(!choice.student.get_placed()) {
                    if (choice.get_marks() == max_marks) {
                        if (choice.student.get_cgpa() > max.student.get_cgpa()) {
                            max = choice;
                        }
                    }
                    if (choice.get_marks() > max_marks) {
                        max = choice;
                        max_marks = choice.get_marks();
                    }
                }
            }
            possible_candidates.remove(max);
            selected.add(max);
        }
        return selected;
    }

}

class Student{
    private int roll_number;
    private float cgpa;
    private String course;
    private boolean placed = false;
    private String company_placed = "";

    Student(int roll_number, float cgpa, String course){
        this.roll_number = roll_number;
        this.cgpa = cgpa;
        this.course = course.replaceAll("^ +| +$|( )+", "$1");;
    }

    void display(){
//        displays information of a student
        System.out.println(roll_number);
        System.out.println(cgpa);
        System.out.println(course);
        if(placed) {
            System.out.println("Placement Status: placed");
            System.out.println("Company: " + company_placed);
        }
        else {
            System.out.println("Placement Status: not placed");
        }
    }

    public void set_placed(String company_placed){
        this.company_placed = company_placed;
        placed = true;
    }

    boolean get_placed(){
        return placed;
    }

    void set_placed(){
        placed = true;
    }

    boolean roll_no_match(int input_roll_no){
        return input_roll_no == roll_number;
    }

    int get_roll_no(){
        return roll_number;
    }

    float get_cgpa(){
        return cgpa;
    }

    void set_company_placed(String company_placed){
        this.company_placed = company_placed;
    }

    String get_course(){
        return course;
    }

    static class student_company{
//        companies that the student applied for
        Company company;
        int marks;
        student_company(Company company, int marks){
            this.company = company;
            this.marks = marks;
        }
    }

    private ArrayList<student_company> possible_companies = new ArrayList<student_company>();

    void add_company(Company company, int marks){
//        addition of company that student applied to
        student_company new_possibility = new student_company(company, marks);
        possible_companies.add(new_possibility);
    }

    void display_applied_companies(){
//        display companies that student applied for along with technical marks
        if (possible_companies.size() <= 0){
            System.out.println("No student with the given roll number has an account.");
        }
        for(student_company c : possible_companies){
            System.out.println(c.company.get_name() + " " + c.marks);
        }
    }

}

public class placement {
    public static void main(String[] args){
        ArrayList<Company> companies = new ArrayList<Company>();
        ArrayList<Student> students = new ArrayList<Student>();
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
//        student input
        for(int i=0; i<n; i++){
            float cgpa = scan.nextFloat();
            String course = scan.nextLine();
            Student new_student = new Student(i+1, cgpa, course);
            students.add(new_student);
        }
        while(true){
            String query_type = scan.nextLine();
            String q[] = query_type.split(" ");
            int query = Integer.parseInt(q[0]);
            if(query == 1){
//                add company
                String company_name = scan.nextLine();
                System.out.print("Number Of Eligible Courses = ");
                int num_courses = scan.nextInt();
                scan.nextLine();
                String[] courses = new String[num_courses];
                for(int i=0; i<num_courses; i++){
                    courses[i] = scan.nextLine();
                }
               System.out.print("Number Of Required Students = ");
                int num_req_students = scan.nextInt();
                Company new_company = new Company(company_name, num_courses, courses, num_req_students);
                new_company.display();
                int count = 1;
                for(Student student : students){
                    for(int i=0; i<num_courses; i++){
                        if(!student.get_placed()) {
//                            getting students that can apply for the company
                            if (courses[i].equals(student.get_course())) {
                                if (count == 1) {
                                    count++;
                                    System.out.println("Enter scores for the technical round.");
                                }
                                System.out.println("Enter score for Roll no. " + student.get_roll_no());
                                int marks = scan.nextInt();
                                student.add_company(new_company, marks);
//                                add this company to student's account
                                new_company.add_student(student, marks);
//                                add student to company's account
                            }
                        }
                    }
                }

                companies.add(new_company);
                scan.nextLine();
            }
            if(query == 2){
//                Remove the accounts of the placed students
                int size = students.size();
                int count = 1;
                int i = 0;
                while(i < size){
                    if (students.get(i).get_placed()){
                        if(count == 1){
                            count++;
                            System.out.println("Accounts removed for ");
                        }
                        System.out.println(students.get(i).get_roll_no());
                        students.remove(students.get(i));
                        size--;
                    }
                    else{
                        i++;
                    }
                }
            }
            if(query == 3){
//                Remove the accounts of companies whose applications are closed
                int size = companies.size();
                int count = 1;
                int i = 0;
                while(i < size){
                    if (!companies.get(i).get_status()){
                        if(count == 1){
                            count++;
                            System.out.println("Accounts removed for ");
                        }
                        System.out.println(companies.get(i).get_name());
                        companies.remove(companies.get(i));
                        size--;
                    }
                    else{
                        i++;
                    }
                }
            }
            if(query == 4){
//                Display number of unplaced students
                int count = 0;
                for(Student student : students){
                    if (!student.get_placed()){
                        count++;
                    }
                }
                System.out.println(count + " students left");
            }
            if(query == 5){
//                Display names of companies whose applications are open
                for (Company company : companies) {
                    if(company.get_status()){
                        System.out.println(company.get_name());
                    }
                }
            }
            if(query == 6){
//                Select students
                String company_name = q[1];
                for (Company company : companies) {
                    if (company.name_match(company_name)) {
                        Company.candidates max = new Company.candidates(students.get(0), 0);
                        ArrayList<Company.candidates> selected= new ArrayList<Company.candidates>();
//                        if(company.getNum_req_students() > students.size()){
////                            all students would get selected
//                            System.out.println("Roll Number of Selected Students");
//                            for (Student element : students) {
//                                System.out.println(element.get_roll_no());
//                                element.set_placed();
////                                student placed
//                                company.close_application();
////                                company application closed
//                            }
//                        }
//                        else {
                        selected = company.top_k(students.get(0), company.getNum_req_students());
//                            getting shortlisted students
                        System.out.println("Roll Number of Selected Students");
                        for (Company.candidates element : selected) {
                            System.out.println(element.student.get_roll_no());
                            element.student.set_placed();
//                                student placed
                            element.student.set_company_placed(company.get_name());
                            company.close_application();
//                                company application closed
                        }
//                        }
                    }
                }
            }
            if(query == 7){
//                Display details of the company
                String company_name_1 = q[1];
                company_name_1.replaceAll("^ +| +$|( )+", "$1");
                for (Company company : companies) {
                    if (company.name_match(company_name_1)) {
                        company.display();
                    }
                }
            }
            if(query == 8){
//                Display details of the student
                int input_roll_number = Integer.parseInt(q[1]);
                for (Student student : students) {
                    if (student.roll_no_match(input_roll_number)) {
                        student.display();
                    }
                }
            }
            if(query == 9){
//                Display names of companies for which the student has applied and their scores in
//technical round of that company
                int input_roll_number = Integer.parseInt(q[1]);
                int flag = 0;
                for (Student student : students) {
                    if (student.roll_no_match(input_roll_number)) {
                        student.display_applied_companies();
                        flag = 1;
                    }
                }
                if(flag == 0){
                    System.out.println("No student with the given roll number has an account.");
                }
            }
        }
    }
}