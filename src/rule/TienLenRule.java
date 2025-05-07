package rule;

import java.util.ArrayList;

import utilities.card.Card;

public abstract class TienLenRule {

	public boolean isValid(ArrayList<Card> chosenCards) {
	    switch (chosenCards.size()) {
	        case 1:
	            return true; // Kiểm tra lá 3 bích ở lượt đầu nếu cần
	        case 2:
	            return isPair(chosenCards);
	        case 3:
	            return isTriple(chosenCards) || isStraight(chosenCards);
	        case 4:
	            return isFourOfAKind(chosenCards) || isStraight(chosenCards);
	        default:
	            return isStraight(chosenCards) || isSequencePair(chosenCards);
	    }
	}
	
	protected abstract boolean isPair(ArrayList<Card> cards);
	
	protected boolean isTriple(ArrayList<Card> cards) {
		if(cards.size() != 3) {
			return false;
		}
	    int cost = cards.get(0).getCost();
	    return cards.stream().allMatch(c -> c.getCost() == cost);
	}
	
	protected boolean isFourOfAKind(ArrayList<Card> cards) {
		if(cards.size() != 4) {
			return false;
		}
	    int cost = cards.get(0).getCost();
	    return cards.stream().allMatch(c -> c.getCost() == cost);
	}
	
	protected abstract boolean isStraight(ArrayList<Card> cards);
	protected abstract boolean isSequencePair(ArrayList<Card> cards);
	public abstract boolean canBeat(ArrayList<Card> preChosenCards, ArrayList<Card> chosenCards);
}
