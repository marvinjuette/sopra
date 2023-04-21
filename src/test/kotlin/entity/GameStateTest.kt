package entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.TestUtils.Companion.getListOfCards
import utils.TestUtils.Companion.getPlayerList

/**
 * This class contains tests to test the functionality of the [GameState] data class
 */
class GameStateTest {

    private lateinit var gameState: GameState
    private val passCounter = 0
    private val currentPlayerIndex = 0
    private val stackCards = getListOfCards(17)
    private val centralCards = getListOfCards(3)
    private val playerList = getPlayerList()

    /**
     * Recreate gameState before every test to ensure that before each test it has the same initial data
     */
    @BeforeEach
    fun init() {
        gameState = GameState(passCounter, currentPlayerIndex, stackCards, centralCards, playerList)
    }

    /**
     * Tests whether the returned passCounter by the gameState is equal to the initial passCounter
     */
    @Test
    fun getPassCounter() {
        assertEquals(passCounter, gameState.passCounter)
    }

    /**
     * Tests whether the reassignment of the passCounter works properly
     */
    @Test
    fun setPassCounter() {
        val newPassCounter = 2

        gameState.passCounter = newPassCounter

        assertEquals(newPassCounter, gameState.passCounter)
    }

    /**
     * Tests whether the returned currentPlayerIndex by the gameState is equal to the initial currentPlayerIndex
     */
    @Test
    fun getCurrentPlayerIndex() {
        assertEquals(currentPlayerIndex, gameState.currentPlayerIndex)
    }

    /**
     * Tests whether the reassignment of the currentPlayerIndex works properly
     */
    @Test
    fun setCurrentPlayerIndex() {
        val newCurrentPlayerIndex = 3

        gameState.currentPlayerIndex = newCurrentPlayerIndex

        assertEquals(newCurrentPlayerIndex, gameState.currentPlayerIndex)
    }

    /**
     * Tests whether the returned stackCards by the gameState is equal to the initial stackCards
     */
    @Test
    fun getStackCards() {
        assertEquals(stackCards, gameState.stackCards)
    }

    /**
     * Tests whether the returned centralCards by the gameState is equal to the initial centralCards
     */
    @Test
    fun getCentralCards() {
        assertEquals(centralCards, gameState.centralCards)
    }

    /**
     * Tests whether the reassignment of the centralCards works properly
     */
    @Test
    fun setCentralCards() {
        val newCentralCards = getListOfCards(3)

        gameState.centralCards = newCentralCards

        assertEquals(newCentralCards, gameState.centralCards)
    }

    /**
     * Tests whether the returned playerList by the gameState is equal to the initial playerList
     */
    @Test
    fun getPlayers() {
        assertEquals(playerList, gameState.players)
    }
}