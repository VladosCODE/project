package ru.vladislav.project1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vladislav.project1.DAO.personDAO;
import ru.vladislav.project1.models.Book;
import ru.vladislav.project1.models.Person;

@Component
public class PersonValidator implements Validator {

    private personDAO personDao;

    @Autowired
    public PersonValidator(personDAO personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDao.show(person.getFio()) != null){
            errors.rejectValue("fio","","Такое име ФИО уже есть");
        }
    }
}
