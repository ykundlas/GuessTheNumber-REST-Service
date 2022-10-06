/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ykundlas.bullsandcows.data;

import com.ykundlas.bullsandcows.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yash
 */
@Repository//@Repository annotation tells Spring this is an injectable bean
public class BullsAndCowsRoundDatabaseDao implements BullsAndCowsRoundDao {

    private final JdbcTemplate jdbcTemplate;

    //The constructor takes JdbcTemplate as a dependency
    @Autowired
    public BullsAndCowsRoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //This method adds a round to the database and returns it.
    @Override
    public Round addRound(Round round) {
        final String sql = "INSERT INTO round(gameId,guess,result) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGameId());
            statement.setString(2, round.getGuess());
            statement.setString(3, round.getResult());
            return statement;

        }, keyHolder);

        round.setRoundId(keyHolder.getKey().intValue());
        final String sqls = "SELECT * FROM Round " + "WHERE roundId=(SELECT max(roundId) FROM Round);";

        LocalDateTime time = (jdbcTemplate.queryForObject(sqls, new BullsAndCowsRoundDatabaseDao.RoundMapper())).getTime();
        round.setTime(time);

        return round;
    }

    //This method a list of all rounds associated with a gameId
    @Override
    public List<Round> findRoundsByGameId(int gameId) {
        final String sql = "SELECT * FROM Round " + "WHERE gameId =? " + "ORDER BY time ASC;";
        return jdbcTemplate.query(sql, new BullsAndCowsRoundDatabaseDao.RoundMapper(), gameId);

    }
    

    //This method turns a row of the resultset into a round object
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            round.setGuess(rs.getString("guess"));
            round.setTime(rs.getTimestamp("time").toLocalDateTime());
            round.setResult(rs.getString("result"));
            return round;
        }
    }
}
