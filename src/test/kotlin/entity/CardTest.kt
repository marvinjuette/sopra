package entity

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals

class CardTest {

   @EnumSource
   @ParameterizedTest(name = "card.suit should equal to {0}")
   fun getCardSuit(cardSuit: CardSuit) {
      val card = Card(cardSuit, CardValue.ACE)
      assertEquals(card.suit, cardSuit)
   }

   @EnumSource
   @ParameterizedTest(name = "card.value should equal to {0}")
   fun getCardValue(cardValue: CardValue) {
      val card = Card(CardSuit.CLUBS, cardValue)
      assertEquals(card.value, cardValue)
   }
}