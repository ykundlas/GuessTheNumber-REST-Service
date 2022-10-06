/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.controllers;

import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import com.ykundlas.bullsandcows.service.BullsAndCowsServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yash
 */
@RestController
@RequestMapping("/api")
public class BullsAndCowsGameController {

    private final BullsAndCowsServiceLayer service;

    @Autowired
    public BullsAndCowsGameController(BullsAndCowsServiceLayer service) {
        this.service = service;
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {

        return service.addGame();
    }

    @GetMapping("/game")
    public List<Game> all() {
        return service.getAllGames();
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> gameById(@PathVariable int gameId) {
        Game result = service.findGameById(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);

    }

    

}
