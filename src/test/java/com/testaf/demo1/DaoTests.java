package com.testaf.demo1;

import com.testaf.demo1.model.Country;
import com.testaf.demo1.model.User;
import com.testaf.demo1.repo.CountryRepository;
import com.testaf.demo1.repo.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DaoTests {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CountryRepository countryRepo;


    @Test
    void contextLoads() {
          }

    @Test
    @DisplayName("Should save and get byId it 's just DAO")
    void whenSaved_thenGetById() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c=countryRepo.getByCode("FR");
        User u=new User();
        u.setUserName("newuser");
        u.setCountry(c);
        u.setBirthdate( LocalDate.parse("16/01/2001", formatter));
        User result=userRepo.save(u);
        assertNotNull( userRepo.getById(result.getId()));
    }

    /**
     *
     */
    @Test
    @DisplayName("Should  save it's just DAO")
    void whenSaved_thenBadCountryError() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c=countryRepo.getByCode("UK");
        User u=new User();
        u.setUserName("newuserUK");
        u.setCountry(c);
        u.setBirthdate( LocalDate.parse("16/01/2001", formatter));
        User result=userRepo.save(u);
        assertNotNull( userRepo.getById(result.getId()));
    }

    @Test
    @DisplayName("Should  save find it's just DAO")
    void whenSaved_thenFindsByObject() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c=countryRepo.getByCode("FR");
        User u=new User();
        u.setUserName("newuser2");
        u.setCountry(c);
        u.setBirthdate( LocalDate.parse("16/01/2001", formatter));
        User result=userRepo.save(u);
        Example<User> example = Example.of(result);
        assertNotNull( userRepo.findOne(example));
    }


    @Test
    @DisplayName("Should verify not null")
    void whenSaved_thenGetListNotNull() {
        List<User> result=userRepo.findAll();
        assertNotEquals(Collections.EMPTY_LIST, result);
    }

    @Test
    @DisplayName("Should give All Users")
    void whenSaved_thenGetAllDatas() {
        List<User> result=userRepo.findAll();
       assertEquals(result.size(), 24);
    }

}
