package com.fuhu.querydsl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private int authorId;
    private String name;
    private String email;
    @OneToMany(targetEntity = Book.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Book> books;
}
