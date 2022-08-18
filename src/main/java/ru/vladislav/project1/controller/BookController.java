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
import ru.vladislav.project1.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final personDAO perDao;
    private final bookDAO bookDao;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(personDAO perDao, bookDAO bookDao, BookValidator bookValidator) {
        this.perDao = perDao;
        this.bookDao = bookDao;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("book",bookDao.index());
        return "book/index_book";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "book/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult){
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors())
            return "book/new";
        bookDao.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,@ModelAttribute("personNew") Person person){
        model.addAttribute("book",bookDao.show(id));
        model.addAttribute("person",perDao.show_have_person(id));
        model.addAttribute("list",perDao.index());
        return "book/show";
    }
    @PatchMapping("/add/{id}")
    public String addHavePerson(@PathVariable("id") int id_book,@ModelAttribute("personNew") Person person){
        bookDao.addHavePerson(id_book,person);
        return "redirect:/book";
    }
    @DeleteMapping("/release/{id}")
    public String deleteHavePerson(@PathVariable("id") int id){
        bookDao.deleteHavePerson(id);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("book",bookDao.show(id));
        return "book/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,@PathVariable("id") int id){
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors())
            return "book/edit";
        bookDao.update(id,book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/book";
    }
}
