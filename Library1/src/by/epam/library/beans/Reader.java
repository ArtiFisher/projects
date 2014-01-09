package by.epam.library.beans;

import java.util.ArrayList;
import java.util.List;


public class Reader {
    private int id;
    private String name;
    private String surname;
    private List<Book> books;

    public Reader() {
        books = new ArrayList<Book>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Reader{" + "id=" + id + " name=" + name + " surname=" + surname + " books=" + books + '}';
    }


}
