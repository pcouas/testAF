package com.testaf.demo1.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
