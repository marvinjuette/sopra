package service

import entity.GameState
import view.Refreshable

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
     * Adds a single refreshable to the list of refeshables of the [GameService] and of the [PlayerActionService].
     *
     * @param newRefreshable [Refreshable] that should be added to the refreshables list
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds all provided refreshables to the list of refrehsables of the [GameService] and of the [PlayerActionService].
     *
     * @param newRefreshables vararg of [Refreshable]s that should be added to the refreshables lists
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

}