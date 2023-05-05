package ru.Denis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Denis.models.Book;
import ru.Denis.models.Person;
import ru.Denis.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> index() {
        return booksRepository.findAll();
    }

    public Optional<Book> show(Integer id) {
        return booksRepository.findById(id);
    }

    public Optional<Person> showOwnerOfBook(Integer id) {
        return Optional.ofNullable(booksRepository.findById(id).get().getOwner());
    }

    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).get().collect(Collectors.toList());
    }

    @Transactional
    public void save(Book newBook) {
        booksRepository.save(newBook);
    }

    @Transactional
    public void update(Book updateBook, int id) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void toFree(int id) {
        booksRepository.findById(id).get().setOwner(null);
    }

    @Transactional
    public void assignBook(int book_id, Person person) {
        booksRepository.findById(book_id).get().setOwner(person);
    }

}
