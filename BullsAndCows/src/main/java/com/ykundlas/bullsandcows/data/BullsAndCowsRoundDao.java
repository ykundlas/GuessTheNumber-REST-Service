/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.data;

import com.ykundlas.bullsandcows.models.Game;
import com.ykundlas.bullsandcows.models.Round;
import java.util.List;

/**
 *
 * @author Yash
 */
public interface BullsAndCowsRoundDao {

    //This method adds a Round object to the database and return it.
    Round addRound(Round round);

    //This method returns a list of al rounds associated with a given gameId
    List<Round> findRoundsByGameId(int gameId);

}
