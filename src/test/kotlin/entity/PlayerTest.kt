package entity

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import utils.TestUtils.getListOfCards
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This class contains tests to ensure the proper functionality of the player data class
 */
class PlayerTest {

    /**
     * This method tests multiple string inputs for the player name
     *
     * Null is not an option here because of kotlin's null safety
     */
    @ParameterizedTest(name = "player.name should be equal to {0}")
    @ValueSource(strings = ["Player 1", "Player 2", "Player 3", "Player 4", "", " "])
    fun getName(name: String) {
        val player = Player(name, false, getListOfCards(3))
        assertEquals(name, player.name)
    }

    /**
     * This method tests all boolean inputs for player hasKnocked
     */
    @ParameterizedTest(name = "player.hasKnocked should be equal to {0}")
    @ValueSource(booleans = [true, false])
    fun getHasKnocked(hasKnocked: Boolean) {
        val player = Player("Player 1", hasKnocked, getListOfCards(3))
        assertEquals(hasKnocked, player.hasKnocked)
    }

    /**
     * This method tests whether the returned handCards by the player object is equal to it's initial handCards
     */
    @Test
    fun getHandCards() {
        val handCards = getListOfCards(3)
        val player = Player("Player 1", false, handCards)

        assertEquals(handCards, player.handCards)
    }

    /**
     * This method tests whether the reassignment of the handCards works properly
     */
    @Test
    fun setHandCards() {
        val initialHandCards = getListOfCards(3)
        val newHandCards = getListOfCards(3)
        val player = Player("Player 1", false, initialHandCards)

        player.handCards = newHandCards

        assertEquals(newHandCards, player.handCards)
    }
}