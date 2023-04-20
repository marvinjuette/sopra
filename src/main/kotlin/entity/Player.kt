package entity

/**
 * Data class to hold all relevant information of a player
 *
 * @property name Represents the player name chosen by the player at game start
 * @property hasKnocked Boolean to represent whether the player has knocked or not
 * @property handCards List of (three) [Card]s the player is currently holding on his hands
 */
data class Player(
    val name: String,
    val hasKnocked: Boolean,
    var handCards: List<Card>,
)