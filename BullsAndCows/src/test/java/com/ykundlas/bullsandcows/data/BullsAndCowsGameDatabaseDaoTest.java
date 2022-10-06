/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.data;

import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Yash
 */

@SpringBootTest
public class BullsAndCowsGameDatabaseDaoTest {
    
    @Autowired
    BullsAndCowsGameDao testGameDao;
    
    @Autowired
    BullsAndCowsRoundDao testRoundDao;
    
    public BullsAndCowsGameDatabaseDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach //clears database before each test
    public void setUp() {
        List<Game> gameList= testGameDao.getAllGames();
        for(Game game : gameList){
           testGameDao.deleteGameById(game.getGameId());
        }  
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addGame method, of class BullsAndCowsGameDatabaseDao.
     */
    @Test
    public void testAddGameFindGameById() {
        //Create a new game object
        Game game = new Game();
        game.setAnswer("1234");
        
        //Add the game to the Dao
        game = testGameDao.addGame(game);
         
        //Get the game from the dao
        Game fromDao = testGameDao.findGameById(game.getGameId());

        // Assertion- Both games should be equal
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAll method, of class BullsAndCowsGameDatabaseDao.
     */
    @Test
    public void testGetAllGames() {
        //Create a new game object
        Game game1 = new Game();
        game1.setAnswer("1112");
        //Add game1 to the Dao
        game1 = testGameDao.addGame(game1);

        //Create a new another game object
        Game game2 = new Game();
        game2.setAnswer("1356");
        //Add game2 to the Dao
        game2 = testGameDao.addGame(game1);

        //Get a list of all games from the Dao
        List<Game> games = testGameDao.getAllGames();

        //Assertion- List size should be 2 and the list should have game1 and game2
        assertEquals(2, games.size());
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of updateGame method, of class BullsAndCowsGameDatabaseDao.
     */
    @Test
    public void testUpdateGame() {
        //Create a new game object
        Game game = new Game();
        game.setAnswer("4536");
        
        //Add the game to the Dao
        game = testGameDao.addGame(game);

        //Get the game from the Dao
        Game fromDao = testGameDao.findGameById(game.getGameId());

        //Assertion- both should be equal and status of game is not finished
        assertEquals(game, fromDao);
        assertEquals(fromDao.isFinished(), false);
        
        //Set the status of game to be finished and update the game
        game.setFinished(true);
        testGameDao.updateGame(game);
        
        //assertion- game and fromDao should not be equal
        assertNotEquals(game, fromDao);

        //Again get the game from the dao
        fromDao = testGameDao.findGameById(game.getGameId());

        //Assertion- both games will be equal and status of fromDao is finished
        assertEquals(game, fromDao);
        assertEquals(fromDao.isFinished(), true);

    }

    @Test
    public void testDeleteGame() {
        //Create a new game object
        Game game = new Game();
        game.setAnswer("1234");
        
         //Add the game to the Dao
        game = testGameDao.addGame(game);
         
        //get the GameId
        int gameId= game.getGameId();
        
         //Create a new Round
        Round round1 = new Round();
        round1.setGuess("1367");
        round1.setResult("e:1:p:1");
        round1.setGameId(gameId);
        //add this round to the dao
        testRoundDao.addRound(round1);
        
        //Create another round
        
        Round round2 = new Round();
        round2.setGuess("3124");
        round2.setResult("e:1:p:3");
        round2.setGameId(gameId);
        //add this round to the dao
        testRoundDao.addRound(round2);
        
        //Delete the game
        testGameDao.deleteGameById(gameId);
        
        //Get the game from the Dao
        Game fromDao = testGameDao.findGameById(gameId);
        
        //Assertion - No game will be found
        assertNull(fromDao);
    }

}
