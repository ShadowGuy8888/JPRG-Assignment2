/*
    Name: Jovan Yap Keat An
    Adm No: p2429407
    Class: DIT/FT/2B/22
*/
package CA2_DIT2B22_JovanYapKeatAn_LauChunYi;

/**
 *
 * @author Jovan
 */
public class Book {

    private String title;
    private String author;
    private Integer ISBN;
    private Double price;
    private String category;
    private Boolean availability;


    public Book(String title, String author, Integer ISBN, Double price, String category, Boolean availability) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.price = price;
        this.category = category;
        this.availability = availability;
    }
    
    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = 0;
        this.price = 0.0;
        this.availability = true;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getISBN() {
        return this.ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getAvailability() {
        return this.availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}