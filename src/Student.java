/**
 * Student
 *
 * I use this class to store one student's name, student number and their marks.
 * It also works out the average and the result symbol, so that logic lives in one place.
 */
public class Student {

    private final String studentNumber;
    private final String name;
    private final int[] marks; // I chose an array because every student has the same number of subjects

    public Student(String studentNumber, String name, int[] marks) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.marks = marks;
    }

    public double getAverage() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        // I cast to double so I don't lose the decimal part when dividing
        return (double) total / marks.length;
    }

    /**
     * I convert the average into a symbol, similar to how my college reported results.
     */
    public String getSymbol() {
        double avg = getAverage();
        if (avg >= 75) return "Distinction";
        if (avg >= 60) return "Merit";
        if (avg >= 50) return "Pass";
        return "Fail";
    }

    /**
     * I turn a student into one line of text so I can save it to a file.
     * Format: number,name,mark1,mark2,mark3
     */
    public String toFileLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(studentNumber).append(",").append(name);
        for (int mark : marks) {
            sb.append(",").append(mark);
        }
        return sb.toString();
    }

    /**
     * I do the opposite of toFileLine(): read one line and build a Student from it.
     * I return null if the line is broken, so one bad line doesn't crash the program.
     */
    public static Student fromFileLine(String line, int subjectCount) {
        String[] parts = line.split(",");
        if (parts.length != 2 + subjectCount) {
            return null;
        }
        try {
            int[] marks = new int[subjectCount];
            for (int i = 0; i < subjectCount; i++) {
                marks[i] = Integer.parseInt(parts[2 + i].trim());
            }
            return new Student(parts[0].trim(), parts[1].trim(), marks);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getStudentNumber() { return studentNumber; }
    public String getName() { return name; }
    public int[] getMarks() { return marks; }
}
