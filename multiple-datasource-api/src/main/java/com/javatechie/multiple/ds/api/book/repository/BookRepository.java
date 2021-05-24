package com.javatechie.multiple.ds.api.book.repository;

import com.javatechie.multiple.ds.api.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
