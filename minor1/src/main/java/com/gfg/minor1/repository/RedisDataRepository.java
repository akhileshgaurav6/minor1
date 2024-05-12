package com.gfg.minor1.repository;

import com.gfg.minor1.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDataRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String BOOK_KEY = "book";

    public void setBookToRedis(Book book){
        setBooToRedisByAuthorName(book);
        setBookToRedisByBookNo(book);
        setBookToRedisByBookType(book);
    }

    public void setBooToRedisByAuthorName(Book book){
        redisTemplate.opsForList().leftPush(BOOK_KEY+book.getAuthor().getName(),book);

    }

    public void setBookToRedisByBookNo(Book book) {

        redisTemplate.opsForValue().set(BOOK_KEY+book.getBookNo(), book);
//        redisTemplate.expire(BOOK_KEY+book.getBookNo(),10, TimeUnit.MINUTES);
    }

    public void setBookToRedisByBookType(Book book) {
        redisTemplate.opsForList().leftPush(BOOK_KEY+book.getType(),book);
//        redisTemplate.expire(BOOK_KEY+book.getType(),10, TimeUnit.MINUTES);
    }


    public List<Book> getBookBYBookNo(String value) {
       return (List<Book>) redisTemplate.opsForList().leftPop(BOOK_KEY+value);
    }
}
