package ru.vladislav.project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vladislav.project1.DAO.bookDAO;
import ru.vladislav.project1.DAO.personDAO;
import ru.vladislav.project1.models.Book;
import ru.vladislav.project1.models.Person;
import ru.vladislav.project1.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final personDAO perDao;
    private final bookDAO book;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(personDAO perDao, bookDAO book, PersonValidator personValidator) {
        this.perDao = perDao;
        this.book = book;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",perDao.index());
        return "person/index_person";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person",perDao.show(id));
        model.addAttribute("book",book.show_books_user(id));
        return "person/show";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "person/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person people,BindingResult bindingResult){
        personValidator.validate(people,bindingResult);
        if (bindingResult.hasErrors())
            return "person/new";
        perDao.save(people);
        return "redirect:/person";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("person",perDao.show(id));
        return "person/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "person/edit";
        perDao.update(id,person);
        return "redirect:/person";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        perDao.delete(id);
        return "redirect:/person";
    }

}
