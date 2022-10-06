/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.service;

import com.ykundlas.bullsandcows.data.BullsAndCowsGameDao;
import com.ykundlas.bullsandcows.data.BullsAndCowsRoundDao;
import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yash
 */
@Service //@Spring annotation tells the Spring that this is a Service class
public class BullsAndCowsServiceLayerImpl implements BullsAndCowsServiceLayer {

    private final BullsAndCowsGameDao gameDao;

    private final BullsAndCowsRoundDao roundDao;

    //The constructor takes gameDao and roundDao as dependencies
    @Autowired
    public BullsAndCowsServiceLayerImpl(BullsAndCowsGameDao gameDao, BullsAndCowsRoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    //This method creates a new game and return the gameId
    @Override
    public int addGame() {
        Game game = new Game();
        game.setAnswer(generateAnswer());
        game = gameDao.addGame(game);
        return game.getGameId();
    }

    //This method adds a round and return the round after making the guess.
    @Override
    public Round addRound(Round round) {
        Game game = gameDao.findGameById(round.getGameId());
        if (game == null) {
            return null;
        }
        //if(game.isFinished()== true){ // if game is finished, round will not be added to the Dao
        if (game.isFinished() == true) {
            throw new GameCompletionException();
        }
        String answer = game.getAnswer();
        String guess = round.getGuess();
        int number = guess.length();
        if(number>4 || number <4){
            throw new CorrectNumberOfDigitsException();
        }
        String result = getResult(answer, guess);
        round.setResult(result);

        if (guess.equals(answer)) { //if guess is correct then game is updated and status of game is finished
            game.setFinished(true);
            gameDao.updateGame(game);
        }

        return roundDao.addRound(round);
    }

    //This method returns a list of all games
    @Override
    public List<Game> getAllGames() {
        List<Game> gameList = gameDao.getAllGames();
        for (Game game : gameList) {
            if (game.isFinished() == false) { //If any game is in progress and 
                //answer of that game is not displayed to the user
                game.setAnswer("");
            }
        }
        return gameList;
    }

    //This method returns the game associated with a given gameId and if the
    //game is in progress, answer of that game is not displayed to the user
    @Override
    public Game findGameById(int gameId) {

        Game game = gameDao.findGameById(gameId);
        if (game == null) {
            return null;
        }
        if (game.isFinished() == false) {
            game.setAnswer(" ");
        }
        return game;

    }

    //This method returns a list all rounds of the game with a given gameId
    @Override
    public List<Round> findRoundsByGameId(int gameId) {
        return roundDao.findRoundsByGameId(gameId);
    }

    //This method is used to generate a four digit number with all four different digits
    public String generateAnswer() {
        Random rng = new Random();
        int a = 0, b = 0, c = 0, d = 0;
        int x = 0;
        while (true) {
            x = rng.nextInt(9000) + 1000;
            a = x % 10; //digit at one's place
            b = (x / 10) % 10; //digit at ten's place
            c = (x / 100) % 10; //digit at hundred's place
            d = x / 1000; // digit at thousand's place
            if (a == b || a == c || a == d || b == c || b == d || c == d) {
                continue;
            } else {
                break;
            }
        }
        String answer = String.valueOf(x);
        return answer;
    }

    //This method is used to calculate the result of a round in the format "e:0:p:0"
    public String getResult(String answer, String guess) {

        // Bull and Cow is defined as two integers. Value of the bull tells exact 
        //digits match between guess and answer and value of the cow tells partial match.
        int bull = 0;
        int cow = 0;

        // we iterate to check all digits of the guess and answer to find exact and partial mathch. 
        //Bull will be incremented for the exact match and cow is incremented for partial match. 
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                bull++;
            } else if (answer.contains(guess.charAt(i) + "")) {
                cow++;
            }
        }

        return "e:" + Integer.toString(bull) + "p:" + Integer.toString(cow);
    }

}
