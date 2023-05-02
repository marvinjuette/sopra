package entity

/**
 * Data class to hold all relevant data regarding the game itself
 *
 * @property passCounter Number of players who have passed in immediate succession
 * @property currentPlayer Index of the current player in the players list
 * @property stackCards List of [Card]s which are used to change central cards
 * @property centralCards List of the (three) [Card]s laying open on the table
 * @property players List of [Player]s in the current game
 */
data class GameState(
	var passCounter: Int,
	var currentPlayer: Int,
	var stackCards: MutableList<Card>,
	var centralCards: MutableList<Card>,
	val players: List<Player>,
)
