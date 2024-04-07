package com.gfg.minor1.repository;

import com.gfg.minor1.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest    // create object by itself
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    public void setUp(){
        author = Author.builder().id(1).email("authorh2@gmail.com").build();
        authorRepository.save(author);  //saving in h2

    }

    @Test
    public void testfindByEmail(){
        Author a = authorRepository.findByEmail("authorh2@gmail.com");
        Assertions.assertEquals(author.getEmail(),a.getEmail());

    }
}
