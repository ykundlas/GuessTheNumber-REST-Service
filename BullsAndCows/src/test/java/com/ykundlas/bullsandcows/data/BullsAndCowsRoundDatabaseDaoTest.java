/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.data;

import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Yash
 */
@SpringBootTest
public class BullsAndCowsRoundDatabaseDaoTest {
    
    @Autowired
    BullsAndCowsGameDao testGameDao;
    
    @Autowired
    BullsAndCowsRoundDao testRoundDao;
    
    
    public BullsAndCowsRoundDatabaseDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
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
     * Test of add method, of class BullsAndCowsRoundDatabaseDao.
     */
    @Test
    public void testAddGetRoundsByGameId() {
        //Create a new Game
        Game game = new Game();
        game.setAnswer("1234");
        
        //Add it to the dao
        game= testGameDao.addGame(game);
        
        //Get the GameId
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
        
        //Get all rounds from the dao
        List<Round> roundList = testRoundDao.findRoundsByGameId(gameId);
        
        //Assertion- The list size should be 2 and it should contain both round1 and round2
        assertEquals(2, roundList.size());
        assertTrue(roundList.contains(round1));
        assertTrue(roundList.contains(round2));
        
    }   
}
