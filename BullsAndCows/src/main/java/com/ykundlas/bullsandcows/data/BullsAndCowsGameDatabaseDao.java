/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.data;

import com.ykundlas.bullsandcows.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Yash
 */
@Repository //@Repository annotation tells Spring this is an injectable bean
public class BullsAndCowsGameDatabaseDao implements BullsAndCowsGameDao {

    private final JdbcTemplate jdbcTemplate;

    //The constructor takes JdbcTemplate as a dependency
    @Autowired
    public BullsAndCowsGameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //This method adds a game to the database and returns it.
    @Override
    public Game addGame(Game game) {
        final String sql = "INSERT INTO game(answer) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> { //JdbcTemplate.update takes two parameter 
            //- PreparedStatementCreator and a KeyHolder

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());

            return statement;

        }, keyHolder);
        game.setGameId(keyHolder.getKey().intValue());
        
        final String SELECT_GAME = "SELECT * FROM Game " + "WHERE gameId = ?;";
        
        Boolean finished = (jdbcTemplate.queryForObject(SELECT_GAME, new BullsAndCowsGameDatabaseDao.GameMapper(), game.getGameId())).isFinished();
        
        game.setFinished(finished);
        return game;
    }

    //This method returns a list of games from the database
    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM Game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    //This method returns a game associated with a gameId
    @Override
    public Game findGameById(int gameId) {
        try {

            final String sql = "SELECT * FROM Game " + "WHERE gameId =?;";
            Game result = jdbcTemplate.queryForObject(sql, new BullsAndCowsGameDatabaseDao.GameMapper(), gameId);

            return result;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //This method ypdates the status of the game 
    @Override
    public void updateGame(Game game) {
        final String sql = "UPDATE Game SET finished = ? " + "WHERE gameId =?;";
        jdbcTemplate.update(sql, game.isFinished(), game.getGameId());
    }

    //This method will delete a game and all rounds associated with that game
    @Override
    @Transactional
    public void deleteGameById(int gameId) {
        final String DELETE_ROUND = "DELETE FROM Round WHERE gameId = ?;";
        jdbcTemplate.update(DELETE_ROUND, gameId);

        final String DELETE_GAME = "DELETE FROM Game WHERE gameId = ?;";
        jdbcTemplate.update(DELETE_GAME, gameId);
    }

    //This method turns a row of the resultset into a game object
    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }

}
