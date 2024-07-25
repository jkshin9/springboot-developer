package me.shinsunyoung.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String example(Model model) {

        Person person = new Person();
        person.setId(1L);
        person.setName("Shin");
        person.setAge(28);
        person.setHobbies(List.of("Programming", "Reading", "Sleeping"));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDate.now());


        return "example";
    }


    @Setter
    @Getter
    class Person {

        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}