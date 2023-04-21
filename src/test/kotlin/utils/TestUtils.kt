package utils

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player

/**
 * This is utility class containing multiple functions to make it easier to write some tests
 */
class TestUtils {
    companion object {

        /**
         * This method is a helper function to make it easier to get a list of random cards
         *
         * @param amountOfCards Int value representing the amount of random cards to create
         */
        fun getListOfCards(amountOfCards: Int): List<Card> {
            val list = mutableListOf<Card>()

            for (i in 1..amountOfCards) {
                val cardSuit = CardSuit.values().random()
                val cardValue = CardValue.values().random()
                list.add(Card(cardSuit, cardValue))
            }

            return list
        }

        /**
         * This method is a helper function to make it easier to get a list of 3 players
         */
        fun getPlayerList(): List<Player> {
            val player1 = Player("Player 1", false, emptyList())
            val player2 = Player("Player 2", false, emptyList())
            val player3 = Player("Player 3", false, emptyList())

            return listOf(player1, player2, player3)
        }
    }
}