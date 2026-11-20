import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * MarksApp
 *
 * A console app I built to capture student marks, show a class report
 * and save everything to a CSV file so the data is still there next time.
 */
public class MarksApp {

    // I keep the subject names in one array so it's easy to change them later.
    private static final String[] SUBJECTS = {"Programming", "Web Development", "Database"};
    private static final String FILE_NAME = "students.csv";

    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile(); // I load saved students first so the user picks up where they left off

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("=== Student Marks Manager ===");
            System.out.println("1. Add student");
            System.out.println("2. Show class report");
            System.out.println("3. Search student");
            System.out.println("4. Show top student");
            System.out.println("5. Delete student");
            System.out.println("0. Save and exit");
            System.out.print("Choice: ");

            String choice = input.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> showReport();
                case "3" -> searchStudent();
                case "4" -> showTopStudent();
                case "5" -> deleteStudent();
                case "0" -> {
                    saveToFile();
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
        System.out.println("Saved. Bye!");
    }

    private static void addStudent() {
        System.out.print("Student number: ");
        String number = input.nextLine().trim();

        // I check for duplicates because two students shouldn't share a number.
        if (findStudent(number) != null) {
            System.out.println("A student with that number already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = input.nextLine().trim();

        // I don't allow commas in the name because I use commas to separate fields in the file.
        if (name.isEmpty() || name.contains(",")) {
            System.out.println("Please enter a name without commas.");
            return;
        }

        int[] marks = new int[SUBJECTS.length];
        for (int i = 0; i < SUBJECTS.length; i++) {
            marks[i] = readMark(SUBJECTS[i]);
        }

        students.add(new Student(number, name, marks));
        System.out.println("Student added.");
    }

    private static void showReport() {
        if (students.isEmpty()) {
            System.out.println("No students captured yet.");
            return;
        }

        System.out.printf("%-10s %-15s", "Number", "Name");
        for (String subject : SUBJECTS) {
            // I only print the first 8 letters so the columns line up
            System.out.printf(" %-8s", subject.length() > 8 ? subject.substring(0, 8) : subject);
        }
        System.out.printf(" %-7s %s%n", "Avg", "Result");

        double classTotal = 0;
        for (Student s : students) {
            System.out.printf("%-10s %-15s", s.getStudentNumber(), s.getName());
            for (int mark : s.getMarks()) {
                System.out.printf(" %-8d", mark);
            }
            System.out.printf(" %-7.1f %s%n", s.getAverage(), s.getSymbol());
            classTotal += s.getAverage();
        }

        System.out.printf("Class average: %.1f%%%n", classTotal / students.size());
    }

    private static void searchStudent() {
        System.out.print("Enter student number: ");
        Student s = findStudent(input.nextLine().trim());
        if (s == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println(s.getName() + " (" + s.getStudentNumber() + ")");
        for (int i = 0; i < SUBJECTS.length; i++) {
            System.out.println("  " + SUBJECTS[i] + ": " + s.getMarks()[i]);
        }
        System.out.printf("  Average: %.1f - %s%n", s.getAverage(), s.getSymbol());
    }

    private static void showTopStudent() {
        if (students.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        // I start with the first student and replace them whenever I find a higher average.
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getAverage() > top.getAverage()) {
                top = s;
            }
        }
        System.out.printf("Top student: %s with %.1f%%%n", top.getName(), top.getAverage());
    }

    private static void deleteStudent() {
        System.out.print("Student number to delete: ");
        Student s = findStudent(input.nextLine().trim());
        if (s == null) {
            System.out.println("Not found.");
            return;
        }
        students.remove(s);
        System.out.println("Deleted " + s.getName() + ".");
    }

    private static Student findStudent(String number) {
        for (Student s : students) {
            if (s.getStudentNumber().equalsIgnoreCase(number)) {
                return s;
            }
        }
        return null;
    }

    // I keep asking until I get a whole number between 0 and 100.
    private static int readMark(String subject) {
        while (true) {
            System.out.print(subject + " mark (0-100): ");
            try {
                int mark = Integer.parseInt(input.nextLine().trim());
                if (mark >= 0 && mark <= 100) {
                    return mark;
                }
            } catch (NumberFormatException e) {
                // I ignore this and just ask again below
            }
            System.out.println("Invalid mark, try again.");
        }
    }

    private static void saveToFile() {
        // try-with-resources closes the file for me, even if something goes wrong
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save the file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromFileLine(line, SUBJECTS.length);
                if (s != null) {
                    students.add(s);
                }
            }
            System.out.println("Loaded " + students.size() + " student(s) from " + FILE_NAME);
        } catch (IOException e) {
            // The first time I run the app the file doesn't exist yet, and that's fine.
            System.out.println("No saved data yet, starting fresh.");
        }
    }
}
