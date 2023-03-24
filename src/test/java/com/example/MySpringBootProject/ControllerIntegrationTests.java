package com.example.MySpringBootProject;

import com.example.MySpringBootProject.beans.Country;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {

    @Test
    @Order(1)
    void getAllCountriesIntegrationTest() throws JSONException {
        String expected = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"countryName\": \"Germany\",\n" +
                "        \"countryCapital\": \"Berlin\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"countryName\": \"USA\",\n" +
                "        \"countryCapital\": \"Washington\"\n" +
                "    }\n" +
                "]";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/getcountries", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    void getCountryByIdIntegrationTest() throws JSONException {
        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"Germany\",\n" +
                "    \"countryCapital\": \"Berlin\"\n" +
                "}";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/getcountries/1", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(3)
    void getCountryByNameIntegrationTest() throws JSONException {
        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"Germany\",\n" +
                "    \"countryCapital\": \"Berlin\"\n" +
                "}";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/getcountries/countryname?name=Germany", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(4)
    void addCountryIntegrationTest() throws JSONException {
        Country country = new Country(3, "Italy", "Rome");

        String expected = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Italy\",\n" +
                "    \"countryCapital\": \"Rome\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/addcountry", request, String.class);

        System.out.println(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(5)
    void updateCountryIntegrationTest() throws JSONException {
        Country country = new Country(3, "Japan", "Tokyo");

        String expected = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Japan\",\n" +
                "    \"countryCapital\": \"Tokyo\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/updatecountry/3", HttpMethod.PUT, request, String.class);

        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(6)
    void deleteCountryIntegrationTest() throws JSONException {
        Country country = new Country(3, "Japan", "Tokyo");

        String expected = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Japan\",\n" +
                "    \"countryCapital\": \"Tokyo\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<Country>(country, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/getcountries/3", HttpMethod.DELETE, request, String.class);

        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        //restTemplate.delete("http://localhost:8081/getcountries/3");
    }
}
