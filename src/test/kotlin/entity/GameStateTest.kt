package entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameStateTest {

    private lateinit var gameState: GameState
    private val passCounter = 0
    private val currentPlayerIndex = 0
    private val stackCards = getRandomStackCards()
    private val centralCards = getRandomCentralCards()
    private val playerList = getPlayerList()

    @BeforeEach
    fun init() {
        gameState = GameState(passCounter, currentPlayerIndex, stackCards, centralCards, playerList)
    }

    @Test
    fun getPassCounter() {
        assertEquals(passCounter, gameState.passCounter)
    }

    @Test
    fun setPassCounter() {
        val newPassCounter = 2

        gameState.passCounter = newPassCounter

        assertEquals(newPassCounter, gameState.passCounter)
    }

    @Test
    fun getCurrentPlayerIndex() {
        assertEquals(currentPlayerIndex, gameState.currentPlayerIndex)
    }

    @Test
    fun setCurrentPlayerIndex() {
        val newCurrentPlayerIndex = 3

        gameState.currentPlayerIndex = newCurrentPlayerIndex

        assertEquals(newCurrentPlayerIndex, gameState.currentPlayerIndex)
    }

    @Test
    fun getStackCards() {
        assertEquals(stackCards, gameState.stackCards)
    }

    @Test
    fun getCentralCards() {
        assertEquals(centralCards, gameState.centralCards)
    }

    @Test
    fun setCentralCards() {
        val newCentralCards = getRandomCentralCards()

        gameState.centralCards = newCentralCards

        assertEquals(newCentralCards, gameState.centralCards)
    }

    @Test
    fun getPlayers() {
        assertEquals(playerList, gameState.players)
    }

    private fun getRandomStackCards(): List<Card> {
        val list = mutableListOf<Card>()

        for (i in 1..17) {
            val cardSuit = CardSuit.values().random()
            val cardValue = CardValue.values().random()
            list.add(Card(cardSuit, cardValue))
        }

        return list
    }

    private fun getRandomCentralCards(): List<Card> {
        val list = mutableListOf<Card>()

        for (i in 1..3) {
            val cardSuit = CardSuit.values().random()
            val cardValue = CardValue.values().random()
            list.add(Card(cardSuit, cardValue))
        }

        return list
    }

    private fun getPlayerList(): List<Player> {
        val player1 = Player("Player 1", false, emptyList())
        val player2 = Player("Player 2", false, emptyList())
        val player3 = Player("Player 3", false, emptyList())

        return listOf(player1, player2, player3)
    }
}