package com.example.MySpringBootProject.repositories;

import com.example.MySpringBootProject.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {

}
