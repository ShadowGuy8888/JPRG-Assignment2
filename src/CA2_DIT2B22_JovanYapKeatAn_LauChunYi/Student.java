/*
    Name: Lau Chun Yi
    Adm No: p2400149
    Class: DIT/FT/2B/22
*/
package CA2_DIT2B22_JovanYapKeatAn_LauChunYi;

import java.util.ArrayList;

/**
 *
 * @author Lau Chun Yi
 */
public class Student extends Person {
    private String adminNumber;
    private ArrayList<Book> books;

    public Student(String adminNumber, String name) {
        this.adminNumber = adminNumber;
        super(name);
        this.books = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void borrowBook(Book book) {
        books.add(book);
        book.setAvailability(false);
    }    

    public void borrowBook(int isbn) {
        Book book = BookManagement.allBooks.stream()
            .filter(b -> b.getISBN() == isbn)
            .findFirst()
            .orElse(null);

        if (book != null) {
            this.borrowBook(book);
            book.setAvailability(false);
        } else 
            System.out.println("Book with ISBN " + isbn + " not found.");
    }

    public void removeBook(int isbn) {
        boolean removed = false;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN() == isbn) {
                books.get(i).setAvailability(true);
                books.remove(i);
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " not found.");
        }
    }

    @Override
    public String toString() {
        return "Student { name: " + this.name + ", admNo: " + this.adminNumber + ", books: " + this.books + " }";
    }
}
