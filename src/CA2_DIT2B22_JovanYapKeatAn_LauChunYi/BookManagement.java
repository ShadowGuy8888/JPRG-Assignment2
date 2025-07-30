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
 * Handles all book-related operations including:
 * - Book inventory management
 * - Search functionality
 * - Cost calculations
 */
public final class BookManagement {

    static ArrayList<Book> allBooks = new ArrayList<>();
    Boolean addBookSuccess = false;

    /**
     * Constructor pre-populates with sample book data
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

            while (scanner.hasNextLine()) {
                // Read book line
                String bookLine = scanner.nextLine().trim();
                String[] bookDetails = bookLine.split(";");
                
                // Add student
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
     * Adds a new book to inventory
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
    
    public ArrayList<Book> getBooks() {
        return BookManagement.allBooks;
    }

    /**
     * Displays all books in formatted HTML table
     */
    public void displayAllBooks() {
        StringBuilder htmlTable = new StringBuilder("<html><table border='1'>");
        htmlTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Availability</th></tr>");
        for (int i=0; i<allBooks.size(); i++) {
            htmlTable.append(String.format(
                "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                allBooks.get(i).getISBN(), 
                allBooks.get(i).getTitle(), 
                allBooks.get(i).getAuthor(), 
                allBooks.get(i).getAvailability()
            ));
        }
        
        htmlTable.append("</table></html>");
        JOptionPane.showMessageDialog(
            null, 
            htmlTable.toString(), 
            "Student List", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Searches for book by exact title match
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
     * Handles new book addition with validation
     */
    public void promptAndAddBook(String title, String author, String ISBN, Double price, String category, Boolean availability) {
        
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
        
        // Check if ISBN is a valid number
        int number;
        try {
            number = Integer.parseInt(ISBN);
        } catch (NumberFormatException e) {
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(null, "ISBN must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate admin number
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
       
            // Price validation
            if (price == null || !String.valueOf(price).matches("^\\d+(\\.\\d+)?$")) {
                AudioPlayer.playSound(System.getProperty("user.dir")+"\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\huh.wav");
                JOptionPane.showMessageDialog(
                    null, 
                    "Please enter a valid number.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                ); return;
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
    
    public void deleteBook(String ISBN) {
        boolean found = false;
        ISBN = ISBN.trim();
    
        for (int i = 0; i < allBooks.size(); i++) {
            Book b = allBooks.get(i);
            if (b.getISBN() == Integer.parseInt(ISBN)) {
                allBooks.remove(i);
                found = true;
                AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\success.wav");
                JOptionPane.showMessageDialog(null, "Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        
        if (!found) {
            AudioPlayer.playSound(System.getProperty("user.dir") + "\\src\\CA2_DIT2B22_JovanYapKeatAn_LauChunYi\\sounds\\fail.wav");
            JOptionPane.showMessageDialog(null, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Calculates and displays total inventory value
     */
    public void showTotalBooksCost() {
        Double totalBooksCost = 0.0;
        for (Book book : allBooks) {
            totalBooksCost += book.getPrice();
        }
        JOptionPane.showMessageDialog(
            null, 
            "Total book cost is $" + totalBooksCost, 
            "Message", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

}
