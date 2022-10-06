# GuessTheNumber-REST-Service
A Spring Boot REST application using JDBC Template to access the database that facilitate playing a number guessing game known as "Bulls and Cows".

REST Endpoints:

"begin" - POST: Starts a game, generates an answer, and sets the correct status.

"guess" – POST: Makes a guess by passing the guess and gameId in as JSON.

"game" – GET: Returns a list of all games. In-progress games do not display their answer.

"game/{gameId}" - GET: Returns a specific game based on ID. In-progress games do not display their answer.

"rounds/{gameId} – GET: Returns a list of rounds for the specified game sorted by time.
