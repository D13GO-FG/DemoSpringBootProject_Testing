package com.example.MySpringBootProject.services;

import com.example.MySpringBootProject.beans.Country;
import com.example.MySpringBootProject.controllers.AddResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CountryService {
    static HashMap<Integer, Country> countryIdMap;

    public CountryService(){
        countryIdMap = new HashMap<Integer, Country>();
        Country mexicoCountry = new Country(1, "Mexico", "Mexico City");
        Country usaCountry = new Country(2, "USA", "Washington");
        Country ukCountry = new Country(3, "UK", "London");

        countryIdMap.put(1, mexicoCountry);
        countryIdMap.put(2, usaCountry);
        countryIdMap.put(3, ukCountry);
    }

    public List getAllCountries(){
        List countries = new ArrayList(countryIdMap.values());
        return countries;
    }

    public Country getCountryById(int id){
        return countryIdMap.get(id);
    }

    public Country getCountryByName(String countryName){
        Country country = null;
        for (int i:countryIdMap.keySet()) {
            if (countryIdMap.get(i).getCountryName().equals(countryName)){
                country = countryIdMap.get(i);
            }
        }
        return country;
    }

    public Country addCountry(Country country){
        country.setId(getMaxId());
        countryIdMap.put(country.getId(), country);
        return country;
    }

    //Utility method to get max id
    public static int getMaxId(){
        int max = 0;
        for (int id:countryIdMap.keySet()) {
            if (max <= id){
                max = id;
            }
        }
        return max + 1;
    }

    public Country updateCountry(Country country){
        if (country.getId() > 0){
            countryIdMap.put(country.getId(), country);
        }
        return country;
    }

    public AddResponse deleteCountry(int id){
        countryIdMap.remove(id);
        AddResponse response = new AddResponse();
        response.setMsg("Country deleted...");
        response.setId(id);
        return response;
    }
}
