package ru.Denis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Denis.models.Book;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
