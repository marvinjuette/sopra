package entity

/**
 * Data class to hold all relevant data regarding the game itself
 *
 * @property passCounter Number of players who have passed in immediate succession
 * @property currentPlayerIndex index of the current in the players list
 * @property stackCards List of [Card]s which are used to change central cards
 * @property centralCards List of the (three) [Card]s laying open on the table
 * @property players List of [Player]s in the current game
 */
data class GameState(
    var passCounter: Int,
    var currentPlayerIndex: Int,
    val stackCards: List<Card>,
    var centralCards: List<Card>,
    val players: List<Player>,
)
