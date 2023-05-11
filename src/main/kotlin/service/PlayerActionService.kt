package service

import entity.Player
import entity.GameState
import utils.removeMultiple
import view.Refreshable

/**
 * This service is intended to manage the 4 different moves a player can choose from.
 *
 * @param rootService Reference to [RootService] to access other classes like the game state.
 */
class PlayerActionService(
    private val rootService: RootService
): AbstractRefreshingService() {

    /**
     * Resets the pass counter in [GameState] data class and marks the player as one who has knocked.
     */
    fun knock(player: Player) {
        resetPassCount()

        player.hasKnocked = true
    }

    /**
     * Handles the pass logic.
     *
     * If all players have passed and there are still enough cards on the stack the open central cards will be exchanged.
     * If all players have passed and there are not enough cards on the stack the game will end.
     * If not all players have passed then the game will continue "normally"
     */
    fun pass() {
        val game = rootService.gameState
        game.passCounter++

        if (game.passCounter != game.players.size) {
            onAllRefreshables { refreshAfterPass() }
            return
        }

        if (game.stackCards.size < 3) {
           return rootService.gameService.finishGame()
        }

        game.centralCards = game.stackCards.removeMultiple(3).toMutableList()
        resetPassCount()

        onAllRefreshables { refreshAfterPass() }
    }

    /**
     * Handles the logic for the exchange of all hand cards with the open central ones.
     */
    fun changeAllCards(player: Player) {
        resetPassCount()

        val centralCards = rootService.gameState.centralCards
        val handCards = player.handCards
        player.handCards = centralCards.also { rootService.gameState.centralCards = handCards }

        onAllRefreshables { refreshAfterChangedAllCards() }
    }

    /**
     * Handles the logic for the change of a single hand card with a single open central card
     *
     * @param player The [Player] who is switching one hand card
     * @param handCardIndex Index of the hand card which is to be exchanged
     * @param centralCardIndex Index of the open central card that is to be taken on hand
     */
    fun changeCard(player: Player, handCardIndex: Int, centralCardIndex: Int) {
        resetPassCount()

        val centralCard = rootService.gameState.centralCards[centralCardIndex]
        val handCard = player.handCards[handCardIndex]

        player.handCards[handCardIndex] = centralCard.also {
            rootService.gameState.centralCards[centralCardIndex] = handCard
        }

        onAllRefreshables { refreshAfterChangedCard() }
    }

    /**
     * Private helper function to set the passCounter to 0
     */
    fun resetPassCount() {
        rootService.gameState.passCounter = 0
    }
}