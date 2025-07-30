/*
    Name: Lau Chun Yi 
    Adm No: p2400149
    Class: DIT/FT/2B/22
*/
package CA2_DIT2B22_JovanYapKeatAn_LauChunYi;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Manages student records including loading from file,
 * adding/removing/searching students, and basic validation.
 */
public final class StudentManagement {
    
    // List of all students managed by the system
    private ArrayList<Student> students = new ArrayList<>();
    
    // Flag to indicate if a student was added successfully
    public boolean addStudentSuccess;

    /**
     * Constructor pre-populates student list by reading from `students.txt`.
     * Also links students to books they’ve borrowed using data from BookManagement.
     */
    public StudentManagement() {
        try {
            Scanner scanner = new Scanner(
                new File(
                    System.getProperty("user.dir") + 
                    "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\students.txt"
                )
            );
            
            // Skip total number of students (first line)
            scanner.nextLine();

            // Read student records until end of file
            while (scanner.hasNextLine()) {
                // Each student line: name;adminNumber;
                String studentLine = scanner.nextLine().trim();
                String[] studentDetails = studentLine.split(";");
                
                // Add student to the internal list
                this.addStudent(studentDetails[1], studentDetails[0]);
                Student currentStudent = this.students.get(this.students.size() - 1);

                // Next line gives number of books borrowed
                int noOfBooksBorrowed = Integer.parseInt(scanner.nextLine().split(";")[0]);

                // Read each borrowed book line and add the corresponding Book object to the currentStudent
                for (int i = 0; i < noOfBooksBorrowed; i++) {
                    String bookLine = scanner.nextLine().trim();
                    String[] bookDetails = bookLine.split(";");
                    
                    // Match ISBN from student record with allBooks in BookManagement
                    currentStudent.borrowBook(
                        BookManagement.allBooks.stream()
                        .filter(b -> b.getISBN().toString().equals(bookDetails[2]))
                        .findFirst()
                        .orElse(null)
                    );
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /**
     * Adds a new student to the system
     * @param adminNumber
     * @param name
     */
    public void addStudent(String adminNumber, String name) {
        Student student = new Student(adminNumber, name);
        this.students.add(student);
    }
    
    /**
     * Deletes a student by their ID from the internal list.
     * Plays a success/failure sound and displays dialog feedback.
     * @param studentID The admin number of the student to delete
     */
    public void deleteStudent(String studentID) {
        boolean found = false;
        studentID = studentID.trim(); // Remove whitespace
    
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getAdminNumber().equalsIgnoreCase(studentID)) {
                students.remove(i);
                found = true;
                
                // Success feedback
                AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\success.wav");
                JOptionPane.showMessageDialog(null, "Student removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        
        if (!found) { // Failure feedback
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(null, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Getter for list of all students.
     * @return List of Student objects
     */
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    /**
     * Searches for students by exact name match.
     * @param searchName The name to search
     * @return List of matched students
     */
    public ArrayList<Student> searchStudentsByName(String searchName) {
        ArrayList<Student> searchedStudents = new ArrayList<>();

        searchName = searchName.trim(); // Clean the searchName input

        // Compare name of each student to search query (case-sensitive)
        for (Student s : this.students) {
            if (s.getName().equals(searchName)) {
                searchedStudents.add(s);
            }
        }

        return searchedStudents;
    }

    /**
     * Adds a student through user input with multiple layers of validation.
     * Plays sounds and shows messages for each validation error/success.
     * @param adminNo Admin number input (should be numeric)
     * @param name Name input (must be >= 3 characters)
     */
    public void promptAndAddStudent(String adminNo, String name) {
        adminNo = adminNo.trim();
        name = name.trim();
        
        // Check for name length
        if (name.length() < 3) {
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\huh.wav");
            JOptionPane.showMessageDialog(null, "Name must be at least 3 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if adminNo is a valid number
        int number;
        try {
            number = Integer.parseInt(adminNo);
        } catch (NumberFormatException e) {
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(null, "Admin number must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate admin number
        for (Student s : this.students) {
            if (s.getAdminNumber().equalsIgnoreCase(adminNo)) {
                AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
                JOptionPane.showMessageDialog(null, "Admin number " + adminNo + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Passed all validation checks, add student
        addStudentSuccess = true;
        this.addStudent(adminNo, name);
        
        // Show success feedback
        AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\success.wav");
        JOptionPane.showMessageDialog(null, "Student added successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
