package entity

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTest {

    @ParameterizedTest(name = "player.name should be equal to {0}")
    @ValueSource(strings = ["Player 1", "Player 2", "Player 3", "Player 4", "", " "])
    fun getName(name: String) {
        val player = Player(name, false, getListOfCards(3))
        assertEquals(name, player.name)
    }

    @ParameterizedTest(name = "player.hasKnocked should be equal to {0}")
    @ValueSource(booleans = [true, false])
    fun getHasKnocked(hasKnocked: Boolean) {
        val player = Player("Player 1", hasKnocked, getListOfCards(3))
        assertEquals(hasKnocked, player.hasKnocked)
    }

    @Test
    fun getHandCards() {
        val handCards = getListOfCards(3)
        val player = Player("Player 1", false, handCards)

        assertEquals(handCards, player.handCards)
    }

    @Test
    fun setHandCards() {
        val initialHandCards = getListOfCards(3)
        val newHandCards = getListOfCards(3)
        val player = Player("Player 1", false, initialHandCards)

        player.handCards = newHandCards

        assertEquals(newHandCards, player.handCards)
    }

    private fun getSampleHandCards(): List<Card> {
        val handCards = mutableListOf<Card>()

        for (i in 1..3) {
            val cardSuit = CardSuit.values().random()
            val cardValue = CardValue.values().random()
            val card = Card(cardSuit, cardValue)
            handCards.add(card)
        }

        return handCards
    }
}