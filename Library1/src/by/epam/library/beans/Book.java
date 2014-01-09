package by.epam.library.beans;


public class Book {
    private int id;
    private int ISBN;
    private String title;
    private String author;
    private int year;
    private int numberOfCopies;

    public Book() {
    }

    public Book(int id, int ISBN, String title, String author, int year, int numberOfCopies) {
        this.id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.year = year;
        this.numberOfCopies = numberOfCopies;
    }

    @Override
    public String toString() {
        return "Book{" + "id = " + id + " ISBN = " + ISBN + " title = " + title + " author = " + author + " year = " + year + " nubber of such Book = " + numberOfCopies + '}';
    }


    public int getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }


}
