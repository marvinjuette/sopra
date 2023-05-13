package entity

import java.util.*

/**
 * Enum to distinguish between the 13 possible values in a french-suited card game:
 * 2-10, Jack, Queen, King, and Ace.
 *
 * The values are ordered according to their most common ordering:
 * 2 < 3 < ... < 10 < Jack < Queen < King < Ace
 *
 * @param score The score the card value scores
 */
enum class CardValue(val score: Int) {
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	JACK(10),
	QUEEN(10),
	KING(10),
	ACE(11),
	;

	/**
	 * provide a single character to represent this value.
	 * Returns one of: 2/3/4/5/6/7/8/9/10/J/Q/K/A
	 */
	override fun toString() =
		when(this) {
			TWO -> "2"
			THREE -> "3"
			FOUR -> "4"
			FIVE -> "5"
			SIX -> "6"
			SEVEN -> "7"
			EIGHT -> "8"
			NINE -> "9"
			TEN -> "10"
			JACK -> "J"
			QUEEN -> "Q"
			KING -> "K"
			ACE -> "A"
		}
}