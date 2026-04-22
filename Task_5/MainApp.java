package Task_5;

import java.io.*;
import java.util.*;

/**
 * Class representing an individual student.
 */
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;
    private String email;

    public Student(String name, int rollNumber, String grade, String email) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.email = email;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getRollNumber() { return rollNumber; }
    public void setRollNumber(int rollNumber) { this.rollNumber = rollNumber; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Roll No: " + rollNumber + " | Name: " + name + " | Grade: " + grade + " | Email: " + email;
    }

    /**
     * Converts student data to a CSV formatted string.
     */
    public String toCSV() {
        return name + "," + rollNumber + "," + grade + "," + email;
    }

    /**
     * Creates a Student object from a CSV formatted string.
     */
    public static Student fromCSV(String csv) {
        String[] parts = csv.split(",");
        if (parts.length == 4) {
            return new Student(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
        }
        return null;
    }
}

/**
 * Class to manage the collection of students.
 */
class StudentManager {
    private List<Student> students;
    private final String dataFile = "Task_5/students.txt";

    public StudentManager() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    public boolean removeStudent(int rollNumber) {
        Student toRemove = searchStudent(rollNumber);
        if (toRemove != null) {
            students.remove(toRemove);
            saveToFile();
            return true;
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    /**
     * Saves the list of students to a text file in CSV format.
     */
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Student student : students) {
                writer.write(student.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Loads the list of students from the text file.
     */
    private void loadFromFile() {
        File file = new File(dataFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromCSV(line);
                if (student != null) {
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}

/**
 * Main application class with User Interface.
 */
public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        boolean exit = false;

        System.out.println("=== Student Management System ===");

        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Edit Student");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            String choiceStr = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    manager.displayAllStudents();
                    break;
                case 5:
                    editStudent();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter Roll Number: ");
        int rollNumber;
        try {
            rollNumber = Integer.parseInt(scanner.nextLine());
            if (manager.searchStudent(rollNumber) != null) {
                System.out.println("Student with this roll number already exists.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
            return;
        }

        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();
        if (grade.trim().isEmpty()) {
            System.out.println("Grade cannot be empty.");
            return;
        }

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) {
            System.out.println("Email cannot be empty.");
            return;
        }

        Student student = new Student(name, rollNumber, grade, email);
        manager.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void removeStudent() {
        System.out.print("Enter Roll Number to remove: ");
        try {
            int rollNumber = Integer.parseInt(scanner.nextLine());
            if (manager.removeStudent(rollNumber)) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
        }
    }

    private static void searchStudent() {
        System.out.print("Enter Roll Number to search: ");
        try {
            int rollNumber = Integer.parseInt(scanner.nextLine());
            Student student = manager.searchStudent(rollNumber);
            if (student != null) {
                System.out.println("Student found: " + student);
            } else {
                System.out.println("Student not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
        }
    }

    private static void editStudent() {
        System.out.print("Enter Roll Number of the student to edit: ");
        try {
            int rollNumber = Integer.parseInt(scanner.nextLine());
            Student student = manager.searchStudent(rollNumber);
            if (student != null) {
                System.out.println("Current details: " + student);
                
                System.out.print("Enter New Name (leave blank to keep current): ");
                String name = scanner.nextLine();
                if (!name.trim().isEmpty()) student.setName(name);

                System.out.print("Enter New Grade (leave blank to keep current): ");
                String grade = scanner.nextLine();
                if (!grade.trim().isEmpty()) student.setGrade(grade);

                System.out.print("Enter New Email (leave blank to keep current): ");
                String email = scanner.nextLine();
                if (!email.trim().isEmpty()) student.setEmail(email);

                manager.saveToFile();
                System.out.println("Student details updated successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid roll number.");
        }
    }
}
