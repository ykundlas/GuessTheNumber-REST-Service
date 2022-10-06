/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.data;

import com.ykundlas.bullsandcows.models.Game;
import java.util.List;

/**
 *
 * @author Yash
 */
public interface BullsAndCowsGameDao {
    
    //Add a game to the database
    Game addGame(Game game);
    
    //Returns all games from the database
    List<Game> getAllGames();
    
    //Returns the game with a given gameId
    Game findGameById(int gameId);
    
    //updates the status of the game 
    void updateGame(Game game);
    
    //delete the game with the given gameId and all rounds related to that game
    void deleteGameById(int gameId);
}
