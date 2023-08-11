package com.apys.learning.springbootsecurity.rest;

import com.apys.learning.springbootsecurity.model.Developer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {
    private List<Developer> DEVELOPER = Stream.of(
            new Developer(1L, "John", "Smith"),
            new Developer(2L, "Katty", "Sark"),
            new Developer(3L, "John", "Dou")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Developer> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable Long id) {
        return null;
    }
}
