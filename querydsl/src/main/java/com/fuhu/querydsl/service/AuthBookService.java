package com.fuhu.querydsl.service;

import com.fuhu.querydsl.dto.AuthorStatistic;
import com.fuhu.querydsl.entity.Author;
import com.fuhu.querydsl.entity.Book;
import com.fuhu.querydsl.repository.AuthorRepository;
import com.fuhu.querydsl.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthBookService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Author> saveAuthorsWithBooks(List<Author> authors){
        return authorRepository.saveAll(authors);
    }
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    public Optional<Author> findAuthorByEmail(String email){
        return authorRepository.findAuthorByEmail(email);
    }

    public List<AuthorStatistic> getAuthorStatistic(){
        return authorRepository.getAuthorStatistic();
    }
    //to
    public List<Author> getAuthorsWithFetchJoin(){
        return authorRepository.getAuthors();
    }
}
