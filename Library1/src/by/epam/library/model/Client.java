package by.epam.library.model;

import java.util.ArrayList;
import java.util.List;


public class Client {
    private int id;
    private String name;
    private String surname;
    private int age;
    private List <Book> books = new ArrayList<Book>();

    public Client() {
    }

    public int getAge() {
        return age;
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


    public void setAge(int age) {
        this.age = age;
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
        return "Client{" + "id=" + id + "name=" + name + "surname=" + surname + "age=" + age + "books=" + books + '}';
    }

   



}
