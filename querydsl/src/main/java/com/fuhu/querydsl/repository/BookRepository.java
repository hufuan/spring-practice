package com.fuhu.querydsl.repository;

import com.fuhu.querydsl.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
