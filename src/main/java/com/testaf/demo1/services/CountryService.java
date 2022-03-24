package com.testaf.demo1.services;

import com.testaf.demo1.model.Country;
import com.testaf.demo1.repo.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
/**
 Gestion des pays
 */
public class CountryService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String badInputParameterErrorMessage = "Bad input parameter";
    public static final String userNotFoundErrorMessage = "Country not found";

    @Autowired
    private CountryRepository countryRepos;

    /**
     * @param code
     * @return
     * @throws IllegalArgumentException Obtenur un pays par son code
     */
    public Country getByCode(final String code) throws IllegalArgumentException, NoSuchElementException {
        log.debug("BEGIN");

        if (code == null) {
            log.error("Exception input parameter {]", code);
            throw new IllegalArgumentException(badInputParameterErrorMessage);
        }

        Country result = countryRepos.getByCode(code);
        if (result != null) {
            log.debug("END ok");
            return result;
        }
        log.error("END ko");
        throw new NoSuchElementException(userNotFoundErrorMessage);

    }
}
