package rules;

import cards.Card;

public interface ICardRule {
	public boolean checkValidate(Card card, Card prevCard);
}
