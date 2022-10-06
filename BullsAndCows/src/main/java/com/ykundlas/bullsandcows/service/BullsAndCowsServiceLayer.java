/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.service;

import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Yash
 */
public interface BullsAndCowsServiceLayer {

    //This method creates a new game and returns the gameId
    int addGame();

    //This method adds a round and returns the round after making the guess.
    Round addRound(Round round);

    //This method returns a list of all games
    List<Game> getAllGames();

    //This method returns the game associated with a given gameId
    Game findGameById(int gameId);

    //This method returns a list of all rounds of the game with a given gameId
    List<Round> findRoundsByGameId(int gameId);

   
    
   
    
}
