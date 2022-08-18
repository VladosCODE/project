package ru.vladislav.project1.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int id_book;
    @Size(min=2,max=30,message="Название книги не может быть меньше 2 символов")
    private String name;
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",message = "Вы неправильно ввели ФИО автора")
    private String author;
    @Min(value=1800,message = "Вы ввели слишком ранний год")
    @Max(value = 2022,message="Данный год ещё не наступил")
    private int year_release;

    public Book(){

    }
    public Book(int id_book, String name, String author, int year_release) {
        this.id_book = id_book;
        this.name = name;
        this.author = author;
        this.year_release = year_release;
    }

    public int getId_book() {
        return id_book;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
    }
}
