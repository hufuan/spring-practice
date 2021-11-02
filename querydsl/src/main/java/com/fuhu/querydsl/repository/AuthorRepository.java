package com.fuhu.querydsl.repository;

import com.fuhu.querydsl.dto.AuthorStatistic;
import com.fuhu.querydsl.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends BaseRepository<Author, Integer> {

    public Optional<Author> findAuthorByEmail(String email);

    public List<AuthorStatistic> getAuthorStatistic();

    public List<Author> getAuthors();
}
