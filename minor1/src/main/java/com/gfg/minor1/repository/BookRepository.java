package com.gfg.minor1.repository;

import com.gfg.minor1.model.Book;
import com.gfg.minor1.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookNo(String bookNo);

    List<Book> findByAuthorName(String authorName);

    List<Book> findByCost(int cost);

    List<Book> findByType(BookType bookType);

    List<Book> findByCostLessThan(int cost);
}
