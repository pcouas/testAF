package com.testaf.demo1.repo;

import com.testaf.demo1.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country getByCode(String fr);
}
