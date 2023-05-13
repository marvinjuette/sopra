package entity

/**
 * Data class to combine both [CardSuit] and [CardValue]
 *
 * @param suit Stores to which [CardSuit] the card belongs
 * @param value Stores to which [CardValue] the card has
 */
data class Card(
	val suit: CardSuit,
	val value: CardValue,
)
