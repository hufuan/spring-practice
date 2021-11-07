package com.fuhu.jpacreatetable.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passenger_infos")
public class PassengerInfos {
    @Id
    @GeneratedValue
    private String name;
    private String sex;
    private Integer age;
}
