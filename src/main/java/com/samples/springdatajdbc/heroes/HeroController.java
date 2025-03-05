package com.samples.springdatajdbc.heroes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final HeroRepository repo;
    private final String hostname;

    public HeroController(HeroRepository repo, @Value("${hostname}") String hostname) {
        this.repo = repo;
        this.hostname = hostname;
    }

    /**
     * Creates a new hero, example:
     * <code>
     * curl -s -X POST "http://localhost:8080/heroes/BruceWayne/35" | jq .
     * curl -s -X POST "http://localhost:8080/heroes/ClarkKent/25" | jq .
     * curl -s -X POST "http://localhost:8080/heroes/BarryAllen/20" | jq .
     * </code>
     */
    @PostMapping("/{name}/{age}")
    public HeroResult create(@PathVariable("name") String name, @PathVariable("age") int age) {
        Hero saved = repo.save(Hero.builder()
                .name(name)
                .age(age)
                .build());
        return new HeroResult(saved, hostname, LocalDateTime.now());
    }

    /**
     * Gets a list of all heroes in the system, example:
     * <code>
     * curl -X GET "http://localhost:8080/heroes" --silent | jq .
     * </code>
     */
    @GetMapping
    public HeroListResult all() {
        List<Hero> heroes = new ArrayList<>();
        repo.findAll()
                .forEach(hero -> heroes.add(hero));
        return new HeroListResult(heroes, hostname, LocalDateTime.now());
    }

    /**
     * Gets a list of all heroes in the system younger than the given age, example:
     * <code>
     * curl -X GET "http://localhost:8080/heroes/lt/30" --silent | jq .
     * </code>
     */
    @GetMapping("/lt/{age}")
    public HeroListResult lessThan(@PathVariable("age") int age) {
        List<Hero> heroes = repo.findByAgeLessThan(age);
        return new HeroListResult(heroes, hostname, LocalDateTime.now());
    }

    /**
     * Gets a list of all heroes in the system older than the given age, example:
     * <code>
     * curl -X GET "http://localhost:8080/heroes/gt/30" --silent | jq .
     * </code>
     */
    @GetMapping("/gt/{age}")
    public HeroListResult greaterThan(@PathVariable("age") int age) {
        List<Hero> heroes = repo.findByAgeGreaterThan(age);
        return new HeroListResult(heroes, hostname, LocalDateTime.now());
    }

    /**
     * Gets a list of all heroes in the system with the given name, example:
     * <code>
     * curl -X GET "http://localhost:8080/heroes/ClarkKent" --silent | jq .
     * </code>
     */
    @GetMapping("/{name}")
    @Cacheable
    public HeroListResult name(@PathVariable("name") String name) {
        List<Hero> found = repo.findByName(name);
        return new HeroListResult(found, hostname, LocalDateTime.now());
    }
}
