package com.gfg.minor1.service;

import com.gfg.minor1.model.*;
import com.gfg.minor1.repository.AuthorRepository;
import com.gfg.minor1.repository.BookRepository;
import com.gfg.minor1.repository.RedisDataRepository;
import com.gfg.minor1.request.BookCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RedisDataRepository redisDataRepository;
    public Book createBook(BookCreateRequest bookCreateRequest) {

        //check if author, which is comingto me from from frontend is already present in my db or not
//        if not present, add author into the db
//        otherwise i will not add one more row into db

        Author authorFromDb = authorRepository.findByEmail(bookCreateRequest.getAuthorEmail());
        if (authorFromDb == null){
            // create a row inside the author table
           authorFromDb = authorRepository.save(bookCreateRequest.toAuthor());
        }

        // create a row inside my book
        Book book = bookCreateRequest.toBook();
        book.setAuthor(authorFromDb);
        book = bookRepository.save(book);
        // push the data into redis as well
        redisDataRepository.setBookToRedis(book);
        return book;

    }

    public List<Book> filter(FilterType filterBy, Operator operator, String value) {
        switch (operator){
            case EQUALS :
                switch (filterBy){
                    case BOOK_NO :
                        // we need to check if data is present in redis or not
                        List<Book> list = redisDataRepository.getBookBYBookNo(value);
                        if(!list.isEmpty()){
                            return list;
                        }
                         list = bookRepository.findByBookNo(value);
                        if(!list.isEmpty()){
                            redisDataRepository.setBookToRedisByBookNo(list.get(0));
                        }
                        return list;
                    case AUTHOR_NAME :
                        return bookRepository.findByAuthorName(value);
                    case COST :
                        return bookRepository.findByCost(Integer.valueOf(value));
                    case BOOKTYPE :
                        return bookRepository.findByType(BookType.valueOf(value));
                }
            case LESS_THAN:
                switch (filterBy){
                    case COST:
                    return bookRepository.findByCostLessThan(Integer.valueOf(value));
                }
            default:
                return new ArrayList<>();
        }
    }

    public void saveUpdate(Book book){
        bookRepository.save(book);
    }
}

// filter by urself for student
// author, should have one more column contact u have to make email nullable true (ddl auto should be update)