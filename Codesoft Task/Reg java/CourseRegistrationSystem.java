import java.util.*;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;
    List<String> registeredStudentIds = new ArrayList<>();

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public boolean isAvailable() {
        return registeredStudentIds.size() < capacity;
    }

    public void displayCourse() {
        System.out.println("Code: " + code);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Capacity: " + registeredStudentIds.size() + "/" + capacity);
        System.out.println("-----------------------------");
    }
}

class Student {
    String id;
    String name;
    List<Course> registeredCourses = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class CourseRegistrationSystem {

    static Map<String, Course> courses = new HashMap<>();
    static Map<String, Student> students = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();

        while (true) {
            System.out.println("\n--- STUDENT COURSE REGISTRATION SYSTEM ---");
            System.out.println("1. View All Courses");
            System.out.println("2. Register Student");
            System.out.println("3. Register for a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. View Student Courses");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1 -> viewCourses();
                case 2 -> registerStudent();
                case 3 -> registerCourse();
                case 4 -> dropCourse();
                case 5 -> viewStudentCourses();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void initializeData() {
        courses.put("CS1011", new Course("CS101", "Intro to Programming", "Basics of Java", 3, "Mon 9AM"));
        courses.put("MA101", new Course("MA101", "Java", "Frontend and Backend", 2, "Tue 10AM"));
        courses.put("PH101", new Course("PH101", "DSA", "Logics and Algorithms", 2, "Wed 11AM"));
    }

    static void viewCourses() {
        System.out.println("\n--- Available Courses ---");
        for (Course c : courses.values()) {
            c.displayCourse();
        }
    }

    static void registerStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        if (students.containsKey(id)) {
            System.out.println("Student already registered.");
            return;
        }
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        students.put(id, new Student(id, name));
        System.out.println("Student registered successfully!");
    }

    static void registerCourse() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);
        if (student == null) {
            System.out.println("Student not found. Please register first.");
            return;
        }

        viewCourses();
        System.out.print("Enter Course Code to Register: ");
        String code = scanner.nextLine();
        Course course = courses.get(code);

        if (course == null) {
            System.out.println("Course not found.");
        } else if (!course.isAvailable()) {
            System.out.println("Course is full.");
        } else if (student.registeredCourses.contains(course)) {
            System.out.println("Already registered in this course.");
        } else {
            student.registeredCourses.add(course);
            course.registeredStudentIds.add(id);
            System.out.println("Successfully registered for " + course.title);
        }
    }

    static void dropCourse() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (student.registeredCourses.isEmpty()) {
            System.out.println("No registered courses.");
            return;
        }

        System.out.println("\n--- Registered Courses ---");
        for (int i = 0; i < student.registeredCourses.size(); i++) {
            Course c = student.registeredCourses.get(i);
            System.out.println((i + 1) + ". " + c.code + " - " + c.title);
        }

        System.out.print("Enter course number to drop: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (index >= 0 && index < student.registeredCourses.size()) {
            Course c = student.registeredCourses.remove(index);
            c.registeredStudentIds.remove(id);
            System.out.println("Dropped course: " + c.title);
        } else {
            System.out.println("Invalid course selection.");
        }
    }

    static void viewStudentCourses() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\n--- Registered Courses for " + student.name + " ---");
        if (student.registeredCourses.isEmpty()) {
            System.out.println("No registered courses.");
        } else {
            for (Course c : student.registeredCourses) {
                System.out.println(c.code + " - " + c.title + " (" + c.schedule + ")");
            }
        }
    }
}

