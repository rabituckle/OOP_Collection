package rules;

import cards.Card;

public class TLMBRule implements ICardRule {
	public boolean checkValidate(Card card, Card prevCard) {
		return card.getScore() > prevCard.getScore();
	}
}
