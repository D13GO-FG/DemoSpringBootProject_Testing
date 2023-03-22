package com.example.MySpringBootProject;

import com.example.MySpringBootProject.beans.Country;
import com.example.MySpringBootProject.controllers.CountryController;
import com.example.MySpringBootProject.services.CountryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {
    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> myCountries;
    Country country;

    @Test
    @Order(1)
    public void test_getAllCountries(){
        myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "Mexico", "Mexico city"));
        myCountries.add(new Country(1, "USA", "Washington"));
        when(countryService.getAllCountries()).thenReturn(myCountries); //Mocking

        ResponseEntity<List<Country>> response = countryController.getCountries();
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById(){
        country = new Country(2, "USA", "Washington");
        int countryId = 2;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryById(countryId);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryId, response.getBody().getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName(){
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryByName(countryName);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(countryName, response.getBody().getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry(){
        country = new Country(3, "Germany", "Berlin");
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(country, response.getBody());
    }

    @Test
    @Order(5)
    public void test_updateCountry(){
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ResponseEntity<Country> response = countryController.updateCountry(countryId, country);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getId());
        assertEquals("Japan", response.getBody().getCountryName());
        assertEquals("Tokyo", response.getBody().getCountryCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry(){
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.deleteCountry(countryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
