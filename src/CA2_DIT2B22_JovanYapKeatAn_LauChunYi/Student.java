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
public class Student { 
    private String name;
    private String adminNumber;
    private ArrayList<Book> books;

    public Student(String adminNumber, String name) {
        this.adminNumber = adminNumber;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

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

    public void removeBook(int index) {
        this.books.remove(index);
    }

    @Override
    public String toString() {
        return "Student { name: " + this.name + ", admNo: " + this.adminNumber + ", books: " + this.books + " }";
    }
}
