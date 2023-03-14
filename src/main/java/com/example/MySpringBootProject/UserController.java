package com.example.MySpringBootProject;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping //Query parameter (filter)
    public String getUsers(@RequestParam(value = "page") int pageNumber, @RequestParam(value = "limit") int limitNumber){
        return "http Get request was sent for page: " + pageNumber + " and limit is: " + limitNumber;
    }

    @GetMapping(path = "/{userID}") //Path parameter (variables)
    public String getUser(@PathVariable String userID){
        return "http Get request was sent for userID: " + userID;
    }

    @PostMapping
    public String createUsers(){
        return "http Post request was sent";
    }

    @PutMapping
    public String updateUsers(){
        return "http Put request was sent";
    }

    @DeleteMapping
    public String deleteUsers(){
        return "http Delete request was sent";
    }
}
