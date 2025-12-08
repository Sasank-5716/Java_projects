package StudentManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StudentManagementSystem {
    static class Student {
        private int id;
        private String name;
        private String grade;
        private double attendance;

        public Student(int id, String name, String grade, double attendance) {
            this.id = id;
            this.name = name;
            this.grade = grade;
            this.attendance = attendance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public double getAttendance() {
            return attendance;
        }

        public void setAttendance(double attendance) {
            this.attendance = attendance;
        }

        @Override
        public String toString() {
            return String.format("ID: %d | Name: %s | Grade: %s | Attendance: %.1f%%",
                    id, name, grade, attendance);

        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Student student = (Student) o;
            return id == student.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class StudentManagement {
        private List<Student> students = new ArrayList<>();
        private int nextId = 1;

        public void addStudent(Scanner scanner) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter grade (A/B/C...): ");
            String grade = scanner.nextLine();
            System.out.print("Enter attendance percentage (e.g. 83 or 83%): ");

            String attStr = scanner.nextLine().trim();
            double attendance = 0;

            try {
                if (attStr.endsWith("%")) {
                    attStr = attStr.substring(0, attStr.length() - 1).trim();
                }
                attendance = Double.parseDouble(attStr);
                if (attendance < 0 || attendance > 100) {
                    System.out.println("❌ Attendance must be 0-100%. Using 0.");
                    attendance = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid attendance format. Using 0%.");
                attendance = 0;
            }

            students.add(new Student(nextId++, name, grade, attendance));
            System.out.println("✓ Student registered successfully! (ID: " + (nextId - 1) + ")");
        }
    }

}
