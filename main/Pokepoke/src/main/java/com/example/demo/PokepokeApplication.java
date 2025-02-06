package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//(exclude = {DataSourceAutoConfiguration.class })
@EntityScan("com.example.demo.model")//←今回追加したアノテーション
@EnableJpaRepositories("com.example.demo.repository")
public class PokepokeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokepokeApplication.class, args);
        //PokemonGame game = new PokemonGame();
        //game.startGame();
    }
}