package com.example.MySpringBootProject.services;

import com.example.MySpringBootProject.beans.Country;
import com.example.MySpringBootProject.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class CountryService {
    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }

    public Country getCountryById(int id){
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for (Country country1:countries){
            if (country1.getId() == id){
                country = country1;
            }
        }
        return country;
        //return countryRepository.findById(id).get();
    }

    public Country getCountryByName(String countryName){
        List<Country> countries = countryRepository.findAll();
        Country country = null;
        for (Country con:countries) {
            if (con.getCountryName().equalsIgnoreCase(countryName)){
                country = con;
            }
        }
        return country;
    }

    public Country addCountry(Country country){
        country.setId(getMaxId());
        countryRepository.save(country);
        return country;
    }

    public Country updateCountry(Country country){
        countryRepository.save(country);
        return country;
    }

    public void deleteCountry(Country country){
        countryRepository.delete(country);
    }

    //Utility method to get max id
    public int getMaxId(){
        return countryRepository.findAll().size() + 1;
    }




}
