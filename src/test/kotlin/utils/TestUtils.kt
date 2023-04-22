package utils

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player

/**
 * This is utility class containing multiple functions to make it easier to write some tests
 */
object TestUtils {

    /**
    * This method is a helper function to make it easier to get a list of random cards
    *
    * @param amountOfCards Int value representing the amount of random cards to create
    */
    fun getListOfCards(amountOfCards: Int): MutableList<Card> {
        val list = mutableListOf<Card>()

        repeat(amountOfCards) {
            list.add(getRandomCard())
        }

        return list
    }

    /**
    * This method is a helper function to make it easier to get a list of 3 players
    */
    fun getPlayerList(): List<Player> {
        val player1 = Player("Player 1", false, getListOfCards(3))
        val player2 = Player("Player 2", false, getListOfCards(3))
        val player3 = Player("Player 3", false, getListOfCards(3))

        return listOf(player1, player2, player3)
    }

    /**
     * This method is a helper function to make it easier to get a random card
     */
    private fun getRandomCard(): Card {
        return Card(CardSuit.values().random(), CardValue.shortDeck().random())
    }
}