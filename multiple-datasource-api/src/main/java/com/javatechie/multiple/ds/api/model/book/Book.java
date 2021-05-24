package com.javatechie.multiple.ds.api.model.book;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BOOK_TB")
public class Book {
    @Id
    private int id;
    private String name;

}
