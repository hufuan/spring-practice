package com.fuhu.jooq.service;

import com.fuhu.JooQDemo.jooq.sample.model.Tables;
import com.fuhu.JooQDemo.jooq.sample.model.tables.pojos.Book;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private DSLContext dslContext;


    public void insertBook(Book book) {
        dslContext.insertInto(Tables.BOOK, Tables.BOOK.ID, Tables.BOOK.TITLE, Tables.BOOK.AUTHOR)
                .values(book.getId(), book.getTitle(), book.getAuthor())
                .execute();
    }

    public List<Book> getBooks() {
        return dslContext.selectFrom(Tables.BOOK)
                .fetchInto(Book.class);
    }
}
