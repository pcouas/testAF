package com.testaf.demo1.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(255)")
    private String userName;

    //@JoinColumn(name = "country_id", columnDefinition = "long")
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", referencedColumnName = "id", columnDefinition = "bigint")
    private Country country;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birthdate", columnDefinition = "date")
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "varchar(1)")
    private Gender gender;

    @Column(name = "phonenumber", columnDefinition = "varchar(10)")
    private String phoneNumber;

}
