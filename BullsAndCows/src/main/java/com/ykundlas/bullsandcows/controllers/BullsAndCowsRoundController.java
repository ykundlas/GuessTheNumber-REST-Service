/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.controllers;

import com.ykundlas.bullsandcows.service.BullsAndCowsServiceLayer;

import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import com.ykundlas.bullsandcows.service.GameCompletionException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class BullsAndCowsRoundController {

    private final BullsAndCowsServiceLayer service;

    public BullsAndCowsRoundController(BullsAndCowsServiceLayer service) {
        this.service = service;
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> createRound(@RequestBody Round round) {

        Round result = service.addRound(round);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> allRounds(@PathVariable int gameId) {
        List<Round> result = service.findRoundsByGameId(gameId);
        return result;
    }

}
