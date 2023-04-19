package entity

data class Player(
    val name: String,
    val hasKnocked: Boolean,
    var handCards: List<Card>,
)