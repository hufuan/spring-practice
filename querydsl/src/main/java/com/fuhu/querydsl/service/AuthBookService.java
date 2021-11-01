package com.fuhu.querydsl.service;

import com.fuhu.querydsl.repository.AuthorRepository;
import com.fuhu.querydsl.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthBookService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
}
