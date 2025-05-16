package BotAI;

import java.util.ArrayList;

import rule.TienLenRule;
import utilities.card.Card;

public class TLBot {
	
	private TienLenRule rule;
	
	//Constructor
	public TLBot(TienLenRule rule) {
        this.rule = rule;
    }
	
	public void deleteCard(ArrayList<Card> playerCard, ArrayList<Card> chosenCard) {
		int j = 0, i = 0;
		while(i < chosenCard.size()) {
			if(chosenCard.get(i).compare(playerCard.get(j)) == 0) {
				playerCard.remove(j);
				i++;
			}
			else {
				j++;
			}
		}
	}
	
	public ArrayList<Card> engineCard(ArrayList<Card> playerCard, ArrayList<Card> preChosenCards) {
		ArrayList<Card> ans = new ArrayList<>();

		if (preChosenCards.size() == 0) {
			// Trường hợp đi đầu: đánh lá  nhỏ nhất
			ans.add(playerCard.get(0));
			return ans;
		} else {
			int preSize = preChosenCards.size();

			// TH1: 1 lá 2*
			if (preSize == 1 && preChosenCards.get(0).getCost() == 15) {
				// Chặt bằng 2* to hơn
				for (Card card : playerCard) {
					if (card.getCost() == 15 && card.compare(preChosenCards.get(0)) > 0) {
						ans.add(card);
						return ans;
					}
				}

				// Chặt bằng tứ quý
				for (int i = 0; i <= playerCard.size() - 4; i++) {
					ArrayList<Card> fourCards = new ArrayList<>(playerCard.subList(i, i + 4));
					if (rule.isValid(fourCards) && rule.canBeat(preChosenCards, fourCards)) {
						return fourCards;
					}
				}

				// Chặt bằng 3 đôi thông
				for (int i = 0; i <= playerCard.size() - 6; i++) {
					ArrayList<Card> sixCards = new ArrayList<>(playerCard.subList(i, i + 6));
					if (rule.isValid(sixCards) && rule.canBeat(preChosenCards, sixCards)) {
						return sixCards;
					}
				}
			}

			// TH2: đôi 2
			else if (preSize == 2 &&
					preChosenCards.get(0).getCost() == 15 &&
					preChosenCards.get(1).getCost() == 15) {

				// Chặt bằng đôi 2 to hơn (ví dụ 2♥ chặt 2♠)
				for (int i = 0; i <= playerCard.size() - 2; i++) {
					ArrayList<Card> twoCards = new ArrayList<>(playerCard.subList(i, i + 2));
					if (rule.isValid(twoCards) && rule.canBeat(preChosenCards, twoCards)) {
						return twoCards;
					}
				}

				// Chặt bằng tứ quý
				for (int i = 0; i <= playerCard.size() - 4; i++) {
					ArrayList<Card> fourCards = new ArrayList<>(playerCard.subList(i, i + 4));
					if (rule.isValid(fourCards) && rule.canBeat(preChosenCards, fourCards)) {
						return fourCards;
					}
				}
			}

			// TH3: các trường hợp còn lại — tìm tổ hợp cùng số lượng
			else {
				for (int i = 0; i <= playerCard.size() - preSize; i++) {
					ArrayList<Card> candidate = new ArrayList<>(playerCard.subList(i, i + preSize));
					if (rule.isValid(candidate) && rule.canBeat(preChosenCards, candidate)) {
						return candidate;
					}
				}
			}
		}
		
		return null; //nếu không chọn được bài để đánh thì bỏ lượt

	}

}
