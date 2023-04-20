package entity

/**
 * Data class to combine both [CardSuit] and [CardValue]
 *
 * @property suit Stores to which [CardSuit] the card belongs
 * @property value Stores to which [CardValue] the card has
 */
data class Card(
    val suit: CardSuit,
    val value: CardValue,
)
