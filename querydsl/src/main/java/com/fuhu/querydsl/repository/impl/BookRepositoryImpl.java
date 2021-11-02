package com.fuhu.querydsl.repository.impl;

import com.fuhu.querydsl.entity.Book;
import com.fuhu.querydsl.repository.BookRepository;

import javax.persistence.EntityManager;

public class BookRepositoryImpl extends  BaseRepositoryImpl<Book, Integer> implements BookRepository {
    public BookRepositoryImpl( EntityManager em) {
        super(Book.class, em);
    }
}
