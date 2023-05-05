package ru.Denis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ru.Denis.models.Book;
import ru.Denis.models.Person;
import ru.Denis.services.BooksService;
import ru.Denis.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    ///////////////////////////////
    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage) {

        if (page == null || booksPerPage == null) {
            model.addAttribute("books", booksService.index());
        } else {
            model.addAttribute("books", booksService.findAll(page, booksPerPage));
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.show(id).get());


        Optional<Person> personOfBook = booksService.showOwnerOfBook(id);
        if (personOfBook.isPresent()) {
            model.addAttribute("personOfBook", personOfBook.get());
        } else {
            model.addAttribute("people", peopleService.index());
        }
        return "books/show";
    }

    ///////////////////////////////
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        booksService.save(book);
        return "redirect:/books";
    }

    ///////////////////////////////
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute(booksService.show(id).get());
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String patch(@ModelAttribute("book") @Valid Book book,
                        BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        booksService.update(book, id);
        return "redirect:/books";
    }

    ///////////////////////////////
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    ///////////////////////////////
    @PatchMapping("/{id}/free")
    public String toFree(@PathVariable("id") int id) {
        booksService.toFree(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/assignBook")
    public String assignBook(@PathVariable("id") int book_id, @ModelAttribute("person") Person person) {
        booksService.assignBook(book_id, person);
        return "redirect:/books/" + book_id;
    }

}
