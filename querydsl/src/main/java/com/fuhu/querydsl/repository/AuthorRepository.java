package com.fuhu.querydsl.repository;

import com.fuhu.querydsl.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "select * from Author where contactNo=?1", nativeQuery= true)
    Author getAuthorByContactNo(String contactNo);
}
