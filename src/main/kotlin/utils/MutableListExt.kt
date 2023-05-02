package utils

import entity.Card

/**
 * Extension function for [MutableList] of Type [Card] to make it easier to get 3 cards from the card stack
 */
fun MutableList<Card>.removeMultiple(amount: Int): List<Card> {
    val removedCards = mutableListOf<Card>()

    repeat(amount) {
        removedCards.add(this.removeFirst())
    }

    return removedCards
}