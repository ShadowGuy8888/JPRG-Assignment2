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
 * @author Lau Chun Yi 
 * Handles all student-related operations including:
 * - Student records management
 * - Book borrowing/returning functionality
 */
public final class StudentManagement {
    private ArrayList<Student> students = new ArrayList<>();

    /**
     * Constructor pre-populates with sample student data
     */
    public StudentManagement() {
        try {
            Scanner scanner = new Scanner(
                new File(
                    System.getProperty("user.dir") + 
                    "\\students.txt"
                )
            );
            
            // Skip total number of students (first line)
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                // Read student line
                String studentLine = scanner.nextLine().trim();
                String[] studentDetails = studentLine.split(";");
                
                // Add student
                this.addStudent(studentDetails[1], studentDetails[0]);
                Student currentStudent = this.students.get(this.students.size() - 1);

                int noOfBooksBorrowed = Integer.parseInt(scanner.nextLine().split(";")[0]);

                // Read book lines
                for (int i = 0; i < noOfBooksBorrowed; i++) {
                    String bookLine = scanner.nextLine().trim();
                    String[] bookDetails = bookLine.split(";");
                    
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
     * Displays all students in formatted HTML table
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(
                null, 
                "No students found.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        StringBuilder htmlTable = new StringBuilder("<html><table border='1'>");
        // First row of table
        htmlTable.append("<tr><th>Admin#</th><th>Name</th><th>Books</th></tr>");
        
        for (Student s : students) { // Subsequent rows of table
            htmlTable.append(String.format(
                "<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                s.getAdminNumber(),
                s.getName(),
                s.getBooks().isEmpty() ? "None" : s.getBooks()
            ));
        }
        // End of table
        htmlTable.append("</table></html>");
        
        JOptionPane.showMessageDialog(
            null, 
            htmlTable.toString(), 
            "Student List", 
            JOptionPane.PLAIN_MESSAGE
        );
    }
    
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    /**
     * Searches for a student by name
     */
    public ArrayList<Student> searchStudentsByName(String searchName) {
        ArrayList<Student> searchedStudents = new ArrayList<>();

        searchName = searchName.trim(); // Clean the searchName input

        // Loop through each student object
        for (Student s : this.students) {
            if (s.getName().equals(searchName)) {
                searchedStudents.add(s);
            }
        }

        return searchedStudents;
    }

    /**
     * Handles new student creation with validation
     */
    public void promptAndAddStudent() {

        String adminNo = null;
        String name = null;

        while (true) {
            Boolean adminNoDuplicated = false; // Reset isDuplicated to false at the start of each iteration
            adminNo = JOptionPane.showInputDialog(
                null, 
                "Enter the new student admin number:", 
                "Input", 
                JOptionPane.QUESTION_MESSAGE
            );
            if (adminNo == null) return; // User clicks close/cancel
            
            adminNo = adminNo.trim(); // Clean the adminNo input
            
            if (!adminNo.matches("^[pP]\\d{6}$")) { // Admin number !match format: p*******
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "Format of Admin Number must be a 'p' followed by six numeric digits.\nE.g., p242482, p219472, p238274", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
            else { // Admin number matches format p*******
                for (Student s : this.students) { // Check for duplicates in admin number
                    if (s.getAdminNumber().equalsIgnoreCase(adminNo)) {
                        adminNoDuplicated = true;
                        break;
                    }
                }
                if (adminNoDuplicated) {
                    AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\fail.wav");
                    JOptionPane.showMessageDialog(
                        null, 
                        "Admin number " + adminNo + " already exists.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                } else break;
            }
        }

        while (true) {
            name = JOptionPane.showInputDialog(
                null, 
                "Enter the new student name:", 
                "Input", 
                JOptionPane.QUESTION_MESSAGE
            );
            if (name == null) return; // User clicks cancel/close

            name = name.trim(); // Clean the name input

            if (name.length() < 3) { // Name is less than 3 characters long
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "Name must be at least 3 characters long.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            } else // Name is at least 3 characters long
                break;
        }
    
        this.addStudent(adminNo, name);
        AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\success.wav");
        JOptionPane.showMessageDialog(
            null, 
            "Student added successfully.", 
            "Message", 
            JOptionPane.INFORMATION_MESSAGE
        );
    
    }

    /**
     * Displays total student count
     */
    public void showTotalStudents() {
        JOptionPane.showMessageDialog(
            null, 
            "Total number of students: " + students.size(), 
            "Message", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Handles book borrowing process
     */
    public void promptAndBorrowBook() {
        Student student = null; // Student object that borrowed the book
        Book borrowedBook = null; // Borrowed book object

        // Student lookup
        while (true) {
            String adminNo = JOptionPane.showInputDialog(
                null, 
                "Adm No", 
                "Input", 
                JOptionPane.QUESTION_MESSAGE
            );
            if (adminNo == null) return; // User clicks cancel/close
            for (Student s : students) {
                if (s.getAdminNumber().equals(adminNo)) {
                    student = s; // Found student object
                    break;
                }
            }
            // admin number found
            if (student != null) break;
            // admin number not found
            else {
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "Admin Number \"" + adminNo + "\" not found.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }

        // Book borrowing
        while (true) {
            String ISBN = JOptionPane.showInputDialog(
                null, 
                "Book ISBN", 
                "Input", 
                JOptionPane.QUESTION_MESSAGE
            );
            if (ISBN == null) return; // User clicks cancel/close
            for (Book b : BookManagement.allBooks) {
                if (ISBN.equals(Integer.toString(b.getISBN()))) {
                    borrowedBook = b;
                    break;
                }
            }
            // Book can be found
            if (borrowedBook != null) {
                if (borrowedBook.getAvailability() == true) {
                    // book is available
                    student.borrowBook(borrowedBook); // This method also sets the book availability to false
                    AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\success.wav");
                    JOptionPane.showMessageDialog(
                        null, 
                        "Book borrowed successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                } else {
                    // book is unavailable
                    AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\fail.wav");
                    JOptionPane.showMessageDialog(
                        null, 
                        "Book ISBN " + ISBN + " is currently unavailable.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }
            // Book cannot be found
            else {
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "ISBN \"" + ISBN + "\" not found.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void promptAndReturnBook() {
        Student student = null; // object of student who wants to return the book object
        Book returnBook = null;

        while (true) {
            // Student lookup
            String adminNo = JOptionPane.showInputDialog(
                null, 
                "Enter student admin number:", 
                "Input", 
                JOptionPane.QUESTION_MESSAGE
            );
            if (adminNo == null) return; // User clicks close/cancel
    
            for (Student s : students) {
                if (s.getAdminNumber().equals(adminNo)) {
                    student = s; // Found student object
                    break;
                }
            }
    
            // Admin number found
            if (student != null) {
                // Student has borrowed books
                if (!student.getBooks().isEmpty()) break;
                // Student has no borrowed books
                else {
                    AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                    JOptionPane.showMessageDialog(
                        null, 
                        "This student has no books to return!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            // Admin number not found
            else {
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "Admin Number \"" + adminNo + "\" not found!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        
        while (true) {
            List<Integer> borrowedBookISBNs = student.getBooks().stream()
                    .map(Book::getISBN)
                    .collect(Collectors.toList());
            List<String> borrowedBookTitles = student.getBooks().stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            String borrowedBookTitlesAndISBNs = "";

            for (int i=0; i<student.getBooks().size(); i++) 
                borrowedBookTitlesAndISBNs += (
                    borrowedBookTitles.get(i) + " (ISBN: " + borrowedBookISBNs.get(i) + ")\n"
                );
            
            String returnBookISBN = JOptionPane.showInputDialog(
                null, 
                String.format(
                    "Books borrowed by %s\n%s\n\nEnter book ISBN to return:", 
                    student.getName(), 
                    borrowedBookTitlesAndISBNs
                ), 
                "Input", 
                JOptionPane.QUESTION_MESSAGE);
    
            if (returnBookISBN == null) return; // User clicks close/cancel

            returnBookISBN = returnBookISBN.trim(); // Clean the input string
    
            for (int i=0; i<student.getBooks().size(); i++) {
                Book b = student.getBooks().get(i);
                if (Integer.toString(b.getISBN()).equals(returnBookISBN)) {
                    student.removeBook(i);
                    returnBook = b; // If student has this book, returnBook is no longer null
                    break;
                }
            }

            // return book ISBN is inside the list of the student's borrowed books
            if (returnBook != null) {
                for (Book b : BookManagement.allBooks) {
                    if (Integer.toString(b.getISBN()).equals(returnBookISBN)) {
                        b.setAvailability(true); // Update book availability
                        break;
                    }
                }
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\success.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "Book returned successfully!", 
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
                // return book title is not inside the list of the student's borrowed books
            } else {
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    student.getName() + " didn't borrow that book!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
