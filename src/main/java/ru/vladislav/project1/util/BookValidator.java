package ru.vladislav.project1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vladislav.project1.DAO.bookDAO;
import ru.vladislav.project1.DAO.personDAO;
import ru.vladislav.project1.models.Book;
import ru.vladislav.project1.models.Person;

@Component
public class BookValidator implements Validator {

    private bookDAO bookDao;

    @Autowired
    public BookValidator(bookDAO bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDao.show(book.getName()) != null){
            errors.rejectValue("name","","Такая книгу уже есть");
        }
    }
}
