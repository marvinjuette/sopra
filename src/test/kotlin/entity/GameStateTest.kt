package entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameStateTest {

    private lateinit var gameState: GameState
    private val passCounter = 0
    private val currentPlayerIndex = 0
    private val stackCards = getListOfCards(17)
    private val centralCards = getListOfCards(3)
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
        val newCentralCards = getListOfCards(3)

        gameState.centralCards = newCentralCards

        assertEquals(newCentralCards, gameState.centralCards)
    }

    @Test
    fun getPlayers() {
        assertEquals(playerList, gameState.players)
    }
}