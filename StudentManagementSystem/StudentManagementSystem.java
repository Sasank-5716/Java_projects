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
    }

}
