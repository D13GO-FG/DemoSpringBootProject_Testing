package com.example.MySpringBootProject;

import com.example.MySpringBootProject.beans.Country;
import com.example.MySpringBootProject.controllers.CountryController;
import com.example.MySpringBootProject.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.restservices.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMvcTests.class})
public class ControllerMockMvcTests {
    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> myCountries;
    Country country;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception {
        myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "Mexico", "Mexico City"));
        myCountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(myCountries);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountries"))
                .andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void test_getCountryById() throws Exception {
        country = new Country(2, "USA", "Washington");
        int countryId = 2;

        when(countryService.getCountryById(countryId)).thenReturn(country);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountries/{id}", countryId))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception {
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";

        when(countryService.getCountryByName(countryName)).thenReturn(country);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountries/countryname").param("name", "USA"))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(print());
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception {
        country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        mockMvc.perform(MockMvcRequestBuilders.post("/addcountry").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void test_updateCountry() throws Exception {
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        mockMvc.perform(MockMvcRequestBuilders.put("/updatecountry/{id}", countryId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
                .andDo(print());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;
        when(countryService.getCountryById(countryId)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/getcountries/{id}", countryId))
                .andExpect(status().isOk());
    }

}
