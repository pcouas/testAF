package com.testaf.demo1.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "country")
public class Country implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(2)")
    private String code;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    private String name;


}
