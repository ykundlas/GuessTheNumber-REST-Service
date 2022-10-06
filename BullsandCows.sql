DROP DATABASE IF EXISTS BullsAndCows;

CREATE DATABASE IF NOT EXISTS BullsAndCows;

USE BullsAndCows;

CREATE TABLE IF NOT EXISTS Game (
    GameId INT PRIMARY KEY AUTO_INCREMENT,
    Answer CHAR(4) NOT NULL,
    Finished BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS Round (
    RoundId INT PRIMARY KEY AUTO_INCREMENT,
    GameId INT NOT NULL,
    Guess CHAR(4) NOT NULL,
    `Time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Result CHAR(7),
    CONSTRAINT fk_Round_Game FOREIGN KEY (GameId)
        REFERENCES Game (GameId)
);

SELECT * FROM Game;