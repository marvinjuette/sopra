package service

import entity.Card
import entity.Player
import entity.CardSuit
import entity.CardValue
import entity.GameState
import extensions.removeMultiple

/**
 * This class is responsible for single games (e.g. preparing the game, keeping track of each player's turn)
 *
 * @param rootService Reference to [RootService] to access other classes like the game state.
 */
open class GameService (
	private val rootService: RootService
): AbstractRefreshingService() {

	private var gameRunning = true

	/**
	 * Prepares the [GameState] data class for the new game (resetting all values, giving each player cards, etc.)
	 */
	fun startNewGame(playerNames: List<String>) {
		val game = rootService.gameState

		rootService.gameState.players = playerNames.map { Player(it, false, mutableListOf()) }
		rootService.playerActionService.resetPassCount()

		game.currentPlayer = 0
		game.stackCards = getAllCardsShuffled()
		game.players.forEach { it.handCards = game.stackCards.removeMultiple(3).toMutableList() }
		game.centralCards = game.stackCards.removeMultiple(3).toMutableList()

		onAllRefreshables { refreshAfterGameStart() }
	}

	/**
	 * Calculates the score of each Player and shows the results of this game (as a scoreboard).
	 */
	open fun finishGame() {
		gameRunning = false

		val scoreMap = mutableMapOf<Player, Double>()
		rootService.gameState.players.forEach { scoreMap[it] = calculateScore(it.handCards) }

		onAllRefreshables { refreshAfterGameEnd() }
	}

	/**
	 * Private helper function to create a 32 pieces card set.
	 */
	private fun getAllCardsShuffled() = MutableList(32) {
		Card(
			CardSuit.values()[it / 8],
			CardValue.values()[(it % 8) + 5]
		)
	}.shuffled().toMutableList()

	/**
	 * Helper function to calculate the score of a set of hand cards on the fly.
	 */
	fun calculateScore(handCards: List<Card>): Double {
		if (handCards[0].value == handCards[1].value && handCards[1].value == handCards[2].value) {
			return 30.5
		}

		return CardSuit.values()
			.map { suit -> handCards.filter { it.suit == suit }.sumOf { it.value.score } }
			.maxOf { it }
			.toDouble()
	}
}