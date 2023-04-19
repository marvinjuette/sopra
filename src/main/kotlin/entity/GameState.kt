package entity

data class GameState(
    var passCounter: Int,
    var currentPlayer: Int,
    var stackCards: List<Card>,
    var centralCards: List<Card>,
    var players: List<Player>,
)
