package com.example.MySpringBootProject;

import com.example.MySpringBootProject.beans.Country;
import com.example.MySpringBootProject.repositories.CountryRepository;
import com.example.MySpringBootProject.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTests.class})
public class ServiceMockitoTests {
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryService countryService;


    @Test
    @Order(1)
    public void test_getAllCountries(){
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "Canada", "Ottawa"));
        myCountries.add(new Country(2, "Russia", "Moscú"));

        when(countryRepository.findAll()).thenReturn(myCountries);//Mocking

        assertEquals(2, countryService.getAllCountries().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById(){
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "Canada", "Ottawa"));
        myCountries.add(new Country(2, "Russia", "Moscú"));
        int countryId = 1;

        when(countryRepository.findAll()).thenReturn(myCountries);//Mocking

        assertEquals(countryId ,countryService.getCountryById(countryId).getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName(){
        List<Country> myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "Canada", "Ottawa"));
        myCountries.add(new Country(2, "Russia", "Moscú"));
        String countryName = "Canada";

        when(countryRepository.findAll()).thenReturn(myCountries);//Mocking

        assertEquals(countryName ,countryService.getCountryByName(countryName).getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry(){
        Country country = new Country(3, "Germany", "Berlin");

        when(countryRepository.save(country)).thenReturn(country);

        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void test_updateCountry(){
        Country country = new Country(3, "Germany", "Berlin");

        when(countryRepository.save(country)).thenReturn(country);

        assertEquals(country, countryService.updateCountry(country));
    }

    @Test
    @Order(6)
    public void test_deleteCountry(){
        Country country = new Country(3, "Germany", "Berlin");

        countryService.deleteCountry(country);

        verify(countryRepository, times(1)).delete(country); //Mocking
    }
}
