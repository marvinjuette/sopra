package service

import entity.GameState

/**
 * This service is used for "inter service communication" as in providing the references to all other relevant classes.
 *
 * @property playerActionService Reference to the initialized [PlayerActionService]
 * @property gameService Reference to the initialized [GameService]
 * @property gameState Reference to the initialized [GameState]
 */
class RootService {

    val playerActionService = PlayerActionService(this)
    val gameService = GameService(this)
    val gameState = GameState(0, 0, mutableListOf(), mutableListOf(), emptyList())

    /**
     * This method takes care of the initialization of the first game (e.g. preparing the ui; creating the player
     * objects after the names where entered by the users.
     */
    fun initializeGame() {
        // TODO ask for playerNames in UI
        // TODO: other ui stuff

        gameService.startNewGame()
    }

}