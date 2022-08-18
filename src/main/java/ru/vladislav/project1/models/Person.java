package ru.vladislav.project1.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Person {
    private int id_user;
    @Pattern(regexp ="[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",message = "Вы неправильно ввели ФИО")
    private String fio;
    @Min(value=1900,message = "Вы ввели слишком ранний год")
    @Max(value = 2022,message="Данный год ещё не наступил")
    private int year_born;
    public Person(){

    }

    public Person(int id_user, String fio, int year_born) {
        this.id_user = id_user;
        this.fio = fio;
        this.year_born = year_born;
    }

    public int getId_user() {
        return id_user;
    }

    public String getFio() {
        return fio;
    }

    public int getYear_born() {
        return year_born;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setYear_born(int year_born) {
        this.year_born = year_born;
    }
}
