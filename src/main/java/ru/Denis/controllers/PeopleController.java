package ru.Denis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PeopleController {
    @GetMapping("/people")
    public String index(){
        return "people/index";
    }
}
