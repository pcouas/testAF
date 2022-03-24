package com.testaf.demo1;

import com.testaf.demo1.controllers.UserController;
import com.testaf.demo1.dto.Param;
import com.testaf.demo1.model.Country;
import com.testaf.demo1.model.Gender;
import com.testaf.demo1.model.User;
import com.testaf.demo1.repo.CountryRepository;
import com.testaf.demo1.services.UserService;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.JUnitRestDocumentation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Demo1ApplicationSprinBootTests {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation( "target/generated-snippets" );


    @Autowired
    UserController userController;

    @Autowired
    private CountryRepository countryRepo;

    @Test
    void contextLoads() {
        assertNotNull(userController);
    }

    /**
     * creation utilisateur Francais
     */
    @Test
    void whenSaved_thenNotNullFR() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getByCode("FR");
        User u = new User();
        u.setUserName("newuserFR");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        assertNotNull(userController.createUser(p));
    }

    /**
     * Creation utilisateur Francais et relecture
     */
    @Test
    void whenSaved_thenFindsByNameFR2() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getByCode("FR");
        User u = new User();
        u.setUserName("newuserFR2");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        userController.createUser(p);
        assertNotNull(userController.getUser(u));
    }

    /**
     * Pas de date pour la creation
     */
    @Test
    void whenSaved_thenFindsByNameFR2SansDate() {
        Country c = countryRepo.getByCode("FR");
        User u = new User();
        u.setUserName("newuserFRWithoutDate");
        u.setCountry(c);
        Param p = new Param();
        p.setUser(u);
        userController.createUser(p);
        assertNotNull(userController.getUser(u));
    }

    @Test
    void whenSaved_thenFindsByNameFR2SansPays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        User u = new User();
        u.setUserName("newuserWithoutCountry");
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        userController.createUser(p);
        assertNotNull(userController.getUser(u));
    }


    /**
     * Creation utilisateur Francais et relecture, mais avec id Country avec comparaison
     */
    @Test
    void whenSaved_thenFindsByNameFR3() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getById(1L);
        User u = new User();
        u.setUserName("newuserFR3");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        ResponseEntity<User> result = userController.createUser(p);
        ResponseEntity<User> result2 = userController.getUser(u);
        assertEquals(result2.getBody(), result.getBody());
    }

    /**
     * Creation utilisateur avec Genre et phoneNumber
     */
    @Test
    void whenSaved_thenFindsByNameFR4() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getByCode("FR");
        User u = new User();
        u.setCountry(c);
        u.setUserName("newuserWithoutCountry");
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        u.setGender(Gender.M);
        u.setPhoneNumber("0601020304");
        Param p = new Param();
        p.setUser(u);
        userController.createUser(p);
        assertNotNull(userController.getUser(u));
    }





    /**
     * Creation utilisateur UK
     */
    @Test
    void whenSaved_thenFindsByNameUKNOTNULL() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getByCode("UK");
        User u = new User();
        u.setUserName("newuserUK1");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        assertNotNull(userController.createUser(p));
    }

    /**
     * NotFoundUser
     */
    @Test
    void whenGet_ByBadUserCountryUKWithErrorMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getByCode("UK");
        User u = new User();
        u.setUserName("newuserUK2A");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        ResponseEntity<Error> responseEntity = userController.getUser(u);
        assertEquals(UserService.userNotFoundErrorMessage, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    /**
     * Bad Inpyt parameter
     */
    @Test
    void whenSaved_thenFindsByNameBadInputNullUserWithErrorMessage() {
        ResponseEntity<Error> responseEntity = userController.getUser(null);
        assertEquals(UserService.badInputParameterErrorMessage, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    /**
     * Bad Country
     */
    @Test
    void whenSaved_exceptionUKUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getByCode("UK");
        User u = new User();
        u.setUserName("newuserUK2");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        ResponseEntity<Error> responseEntity = userController.createUser(p);

        assertEquals(UserService.notFrenchErrormessage, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    /**
     *
     */
    @Test
    void whenSaved_exceptionTooYoung() {
        Country c = countryRepo.getByCode("FR");
        User u = new User();
        u.setUserName("newuserFR3");
        u.setCountry(c);
        u.setBirthdate(LocalDate.now().minusDays(721));
        Param p = new Param();
        p.setUser(u);
        ResponseEntity<Error> responseEntity = userController.createUser(p);
        assertEquals(UserService.youngErrorMessage, Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }


    /**
     * Creation utilisateur Francais et relecture, mais avec id Country
     */
    @Test
    @DisplayName("Should save and get byId it 's just DAO")
    void whenSaved_thenFindsBadCountry() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Country c = countryRepo.getById(999L); //n'existe pas
        User u = new User();
        u.setUserName("newuserFR3");
        u.setCountry(c);
        u.setBirthdate(LocalDate.parse("16/01/2001", formatter));
        Param p = new Param();
        p.setUser(u);
        ResponseEntity<Error> responseEntity = userController.createUser(p);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, Objects.requireNonNull(responseEntity.getStatusCode()));

    }
}
