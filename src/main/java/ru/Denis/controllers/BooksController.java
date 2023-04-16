package ru.Denis.controllers;


import org.springframework.web.bind.annotation.*;
import ru.Denis.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.Denis.models.Book;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;


    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    ///////////////////////////////
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("personOfBook", bookDAO.showPersonOfBook(id));
        return "books/show";
    }

    ///////////////////////////////
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }

    ///////////////////////////////
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute(bookDAO.show(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String patch(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    ///////////////////////////////
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    ///////////////////////////////
    @PatchMapping("/{id}/free")
    public String toFree(@PathVariable("id") int id) {
        bookDAO.toFree(id);
        return "redirect:/books";
    }

}
