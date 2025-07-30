/*
    Name: Jovan Yap Keat An
    Adm No: p2429407
    Class: DIT/FT/2B/22
*/
package CA2_DIT2B22_JovanYapKeatAn_LauChunYi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * @author Jovan
 */

/**
 * BookManagement class handles the creation, loading, 
 * validation, searching, and deletion of book records.
 */
public final class BookManagement {

    // Shared static list of all books in the system
    static ArrayList<Book> allBooks = new ArrayList<>();
    
    // Flag used to check whether a book was added successfully
    Boolean addBookSuccess = false;

    /**
     * Constructor loads initial book data from a text file.
     * File must follow a specific format (see below).
     */
    public BookManagement() {
        try {
            Scanner scanner = new Scanner(
                new File(
                    System.getProperty("user.dir") + 
                    "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\books.txt"
                )
            );
            
            // Skip total number of books (first line)
            scanner.nextLine();

            // Read and parse each book entry line
            while (scanner.hasNextLine()) {
                String bookLine = scanner.nextLine().trim();
                String[] bookDetails = bookLine.split(";");
                
                // Create and add book using parsed values
                this.addBook(
                    bookDetails[0], 
                    bookDetails[1], 
                    Integer.parseInt(bookDetails[2]), 
                    Double.parseDouble(bookDetails[3]), 
                    bookDetails[4], 
                    Boolean.parseBoolean(bookDetails[5])
                );
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Adds a new book to library.
     * @param title
     * @param author
     * @param ISBN
     * @param price
     * @param category
     * @param availability
     */
    public void addBook(String title, String author, Integer ISBN, Double price, String category, Boolean availability) {
        Book book = new Book(title, author, ISBN, price, category, availability);
        allBooks.add(book);
    }
    
    /**
     * Retrieves all books in the system.
     * @return List of Book objects
     */
    public ArrayList<Book> getBooks() {
        return BookManagement.allBooks;
    }

    /**
     * Searches for books by exact title.
     * @param searchTitle Title to search for
     * @return List of matching books
     */
    public ArrayList<Book> searchBooksByTitle(String searchTitle) {
        ArrayList<Book> searchedBooks = new ArrayList<>();

        searchTitle = searchTitle.trim(); // clean the searchTitle input

        // Loop through all the library book objects to find the book
        for (Book b : allBooks) {
            if (b.getTitle().equals(searchTitle)) {
                searchedBooks.add(b);
            }
        }

        return searchedBooks;
    }

    /**
     * Adds a new book after validating all input fields.
     * Displays appropriate dialogs and sounds for errors or success.
     * @param title Book title
     * @param author Book author
     * @param ISBN ISBN (as String, validated later)
     * @param price Book price
     * @param category Book category
     * @param availability Initial availability status
     */
    public void promptAndAddBook(String title, String author, String ISBN, Double price, String category, Boolean availability) {
        
        // Title validation
        if (title == null || title.length() < 3) {
            AudioPlayer.playSound(System.getProperty("user.dir")+"\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\huh.wav");
            JOptionPane.showMessageDialog(
                null, 
                "Book title must be at least 3 characters long.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            ); return;
        }

        // Author validation
        if (author == null || author.length() < 3) {
            AudioPlayer.playSound(System.getProperty("user.dir")+"\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\huh.wav");
            JOptionPane.showMessageDialog(
                null, 
                "Book author must be at least 3 characters long.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            ); return;
        }
        
        // ISBN validation
        int number;
        try {
            number = Integer.parseInt(ISBN);
        } catch (NumberFormatException e) {
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(null, "ISBN must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Duplicate ISBN check
        for (Book b : allBooks) {
            if (b.getISBN() == number) {
                AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
                JOptionPane.showMessageDialog(
                        null, 
                        "ISBN " + ISBN + " already exists.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                ); return;
            }
        }
       
        // Price validation (must be numeric and >= $5)
        if (price == null || !String.valueOf(price).matches("^\\d+(\\.\\d+)?$")) {
            AudioPlayer.playSound(System.getProperty("user.dir")+"\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\huh.wav");
            JOptionPane.showMessageDialog(
                null, 
                "Please enter a valid number.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            ); 
            return;
            
        } else if (price < 5.0) { 
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(
                null, 
                "Minimum price is $5.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            ); return;
        }

        // Category validation
        if (category == null || category.length() < 3) {
            AudioPlayer.playSound(System.getProperty("user.dir")+"\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\huh.wav");
            JOptionPane.showMessageDialog(
                null, 
                "Book category must be at least 3 characters long.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            ); return;
        } 

        addBookSuccess = true;
        this.addBook(title, author, number, price, category, true);
        AudioPlayer.playSound(System.getProperty("user.dir")+"\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\success.wav");
        JOptionPane.showMessageDialog(
            null, 
            "Book Added", 
            "Message", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Deletes a book from the system using its ISBN.
     * @param ISBN ISBN number of book to delete
     */
    public void deleteBook(String ISBN) {
        boolean found = false;
        ISBN = ISBN.trim();
    
        for (int i = 0; i < allBooks.size(); i++) {
            Book b = allBooks.get(i);
            if (b.getISBN() == Integer.parseInt(ISBN)) {
                allBooks.remove(i);
                found = true;
                
                // Success feedback
                AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\success.wav");
                JOptionPane.showMessageDialog(null, "Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        
        if (!found) { // Failure feedback
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(null, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
