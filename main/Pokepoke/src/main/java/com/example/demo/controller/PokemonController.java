package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pokemon;
import com.example.demo.service.PokemonService;

@RestController
public class PokemonController {
    private final PokemonService pokemonService = new PokemonService();

    @GetMapping("/pokemon")
    public Pokemon getPokemon(@RequestParam int id) {
        return pokemonService.getPokemon(id);
    }
    
    @GetMapping("/damage")
    public Double calcDamage(@RequestParam String from, String to) {
    	System.out.println(pokemonService.caclDamage(from, to));
        return pokemonService.caclDamage(from, to);
    }
}