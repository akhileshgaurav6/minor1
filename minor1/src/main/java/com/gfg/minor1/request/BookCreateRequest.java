package com.gfg.minor1.request;

import com.gfg.minor1.model.Author;
import com.gfg.minor1.model.Book;
import com.gfg.minor1.model.BookType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequest {

    @NotNull(message = "book name must not ne null")
    private String name;

    @NotNull(message = "book no must not ne null")
    private String bookNo;

    @Positive
    private int cost;

    private BookType type;


    private String authorName;

    private String authorEmail;

    public Author toAuthor() {
        return Author.builder().
                name(this.authorName).
                email(this.authorEmail).
                build();      // this build create object of author class internally
    }

    public Book toBook() {
        return Book.builder().
                name(this.name).
                bookNo(this.bookNo).
                cost(this.cost).
                type(this.type).
                build();
    }
}
