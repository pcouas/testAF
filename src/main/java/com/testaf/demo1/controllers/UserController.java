package com.testaf.demo1.controllers;

import com.testaf.demo1.dto.Param;
import com.testaf.demo1.model.Country;
import com.testaf.demo1.model.User;
import com.testaf.demo1.services.CountryService;
import com.testaf.demo1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

   @Autowired
    private UserService userService;
  //  private final UserService userService;


    @Autowired
    private CountryService countryService;
/*
    public UserController(UserService userService) {
        this.userService = userService;
    }*/

    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World from Spring Boot";
    }

    /**
     * @param param
     * @return
     */
    @PostMapping("/create")
    @Transactional
    public ResponseEntity createUser(@RequestBody Param param) {
        User user = param.getUser();
        String countryCode = param.getCountryCode();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            if (countryCode != null) {
                log.info("countryCode {}", countryCode);
                try {
                    Country country = countryService.getByCode(countryCode);
                    user.setCountry(country);
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
                } catch (NoSuchElementException e) {
                    return new ResponseEntity(new Error(e.getMessage()), HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param user
     * @param countryCode
     * @return
     */
    @GetMapping(value = "/find")
    public ResponseEntity getUser(User user, String... countryCode) {
        try {
            if (user!=null && countryCode != null && countryCode.length>0 && (countryCode[0] instanceof String)) {
                log.info("countryCode {}", countryCode[0]);
                Country country = countryService.getByCode(countryCode[0]);
                user.setCountry(country);
            }
            return ResponseEntity.ok(userService.findByUser(user));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findall")
    public ResponseEntity getUsers(User user, String... countryCode) {
        try {
            if (user!=null && (countryCode[0] instanceof String)) {
                log.info("countryCode {}", countryCode[0]);
                Country country = countryService.getByCode(countryCode[0]);
                user.setCountry(country);
            }

            return ResponseEntity.ok(userService.findAllByUser(user));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(new Error(e.getMessage()), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
