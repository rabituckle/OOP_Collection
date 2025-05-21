package rule;

import java.util.ArrayList;

import utilities.card.Card;

public class TLMBRule extends TienLenRule {
	
	@Override
	public boolean canBeat(ArrayList<Card> preChosenCards, ArrayList<Card> chosenCards) {
		if(!isValid(chosenCards)) {
			return false;
		}
		
	    int preSize = preChosenCards.size();
	    int currSize = chosenCards.size();

	    // So sánh thông thường: cùng loại, cùng số lượng
	    if (preSize == currSize) {
	    	if(preSize == 1) {
	    		if(chosenCards.get(0).getCost() == 15) {
	    			return chosenCards.get(0).compare(preChosenCards.get(0)) > 0;
	    		}
	    		if(!chosenCards.get(0).getSuit().equals(preChosenCards.get(0).getSuit())) {
	    			return false;
	    		}
	    		return chosenCards.get(0).compare(preChosenCards.get(0)) > 0;
	    	}
	    	
	        if (preSize == 2) { // Can xet truong hop chan bang doi 2
	        	if(chosenCards.get(0).getCost() == 15) {
	        		return chosenCards.get(1).compare(preChosenCards.get(1)) > 0;
	        	}
	        	if(chosenCards.get(0).getColor() != preChosenCards.get(0).getColor()) {
	    			return false;
	    		}
	            return chosenCards.get(1).compare(preChosenCards.get(1)) > 0;
	        }
	        
	        if (isTriple(preChosenCards) && isTriple(chosenCards)) {
	        	for(int i = 0; i < preSize; i++) {
	        		if(!chosenCards.get(i).getSuit().equals(preChosenCards.get(i).getSuit())) {
		    			return false;
		    		}
	        	}
	            return chosenCards.get(2).compare(preChosenCards.get(2)) > 0;
	        }
	        
	        if (isFourOfAKind(preChosenCards) && isFourOfAKind(chosenCards)) {
	            return chosenCards.get(3).compare(preChosenCards.get(3)) > 0;
	        }
	        
	        if (isStraight(preChosenCards) && isStraight(chosenCards)) {
	        	if(!chosenCards.get(0).getSuit().equals(preChosenCards.get(0).getSuit())) {
	    			return false;
	    		}
	            return chosenCards.get(currSize - 1).compare(preChosenCards.get(preSize - 1)) > 0;
	        }
	        
	        if (isSequencePair(preChosenCards) && isSequencePair(chosenCards)) {
	        	if(chosenCards.get(0).getColor() != preChosenCards.get(0).getColor()) {
	    			return false;
	    		}
	            return chosenCards.get(currSize - 1).compare(preChosenCards.get(preSize - 1)) > 0;
	        }
	    }

	    // Luật chặt đặc biệt: chặt 1 lá 2
	    if (preSize == 1 && preChosenCards.get(0).getCost() == 15) {
	        if (isFourOfAKind(chosenCards)) return true;
	        if (isSequencePair(chosenCards)) return true;
	    }

	    // Chặt đôi 2
	    if (isPair(preChosenCards) &&
	        preChosenCards.get(0).getCost() == 15 &&
	        preChosenCards.get(1).getCost() == 15) {
	        if (isFourOfAKind(chosenCards)) return true;
	        if (isSequencePair(chosenCards) && chosenCards.size() >= 10) return true;
	    }
	    
	    // Chặt tứ quý
	    if (isFourOfAKind(preChosenCards)) {
	    	if (isSequencePair(chosenCards) && chosenCards.size() >= 10) return true;
	    }
	    
	    // Chặt đôi thông (Không chặt được 5 đôi thông trở lên)
	    if(isSequencePair(preChosenCards) && preSize <= 8) {
	    	if (isFourOfAKind(chosenCards)) return true;
	    }

	    return false;
	}

	@Override
	protected boolean isPair(ArrayList<Card> cards) {
		if(cards.size() != 2) {
			return false;
		}
		if(cards.get(0).getColor() != cards.get(1).getColor()) {
			return false;
		}
	    return cards.get(0).getCost() == cards.get(1).getCost();
	}
	
	@Override
	public boolean isStraight(ArrayList<Card> cards) {
		if (cards.size() < 3) return false;
	    // Không được chứa lá 2 (cost = 15)
	    for (Card c : cards) {
	        if (c.getCost() == 15) return false;
	    }

	    for (int i = 0; i < cards.size() - 1; i++) {
	        Card current = cards.get(i);
	        Card next = cards.get(i + 1);
	        if (next.getCost() != current.getCost() + 1) return false;
	        if (!next.getSuit().equals(current.getSuit())) {
	        	return false;
	        }
	    }
	    return true;
	}
	
	@Override
	protected boolean isSequencePair(ArrayList<Card> cards) {
		int n = cards.size();
	    if (n < 6 || n % 2 != 0) return false;

	    // Không chứa lá 2
	    for (Card c : cards) {
	        if (c.getCost() == 15) return false;
	    }

	    for (int i = 0; i < n; i += 2) {
	        // Mỗi cặp phải là đôi
	        if (cards.get(i).getCost() != cards.get(i + 1).getCost()) return false;
	        if (cards.get(i).getColor() != cards.get(i + 1).getColor()) {
	        	return false;
	        }

	        // Tăng dần theo rank
	        if (i > 0) {
	            Card prevCard = cards.get(i - 2);
	            Card currCard = cards.get(i);
	            if (currCard.getCost() != prevCard.getCost() + 1) return false;
	            if (currCard.getColor() != prevCard.getColor()) {
	            	return false;
	            }
	        }
	    }

	    return true;
	}
}
