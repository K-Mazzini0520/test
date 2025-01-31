package com.example.demo.service;

import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PokemonService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://pokeapi.co/api/v2/pokemon/";
    private final String jpUrl = "https://pokeapi.co/api/v2/pokemon-species/";
    private final String typeUrl = "https://pokeapi.co/api/v2/type/";
    
    public Pokemon getPokemon(int id) {
        String url = baseUrl + id;
        Pokemon pk = new Pokemon();
        pk = restTemplate.getForObject(url, Pokemon.class);
        String res = restTemplate.getForObject(jpUrl + id, String.class);
        //System.out.println(res);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(res);
            JsonNode namesNode = rootNode.path("names");

            for (JsonNode nameNode : namesNode) {
                if (nameNode.path("language").path("name").asText().equals("ja-Hrkt")) {
                    String japaneseName = nameNode.path("name").asText();
                    pk.setName(japaneseName);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //pk.setName("さいきょう");
        return pk;
        //return restTemplate.getForObject(url, Pokemon.class);
    }
    
    public Double caclDamage(String t1, String t2) {
    	String url = typeUrl + t1;
    	String res = restTemplate.getForObject(url, String.class);
    	Double x =1.0;
    	try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(res);
            JsonNode namesNode = rootNode.path("damage_relaions");

            for (JsonNode nameNode : namesNode) {
                if (nameNode.path("double_damage_to").asText().equals(t2)) {
                    x *= 2;
                    break;
                }
            }
            for (JsonNode nameNode : namesNode) {
                if (nameNode.path("half_damage_to").asText().equals(t2)) {
                    x *= 0.5;
                    break;
                }
            }
            for (JsonNode nameNode : namesNode) {
                if (nameNode.path("no_damage_to").asText().equals(t2)) {
                    x *= 0.0;
                    break;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    	//System.out.println(x);
    	return x;
    }    
}