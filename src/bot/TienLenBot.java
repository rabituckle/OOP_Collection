package bot;

import java.util.ArrayList;

import rule.TienLenRule;
import utilities.card.Card;

public class TienLenBot {
	
	private TienLenRule rule;
	
	public TienLenBot(TienLenRule rule) {
		this.rule = rule;
	}
	
	public ArrayList<Card> engineCard(ArrayList<Card> playerCard, ArrayList<Card> pre) {
		// New round
		System.out.println(playerCard);
		if(pre.size() == 0) {
			ArrayList<Card> ans = new ArrayList<Card>();
			ans.add(playerCard.get(0));
			playerCard.remove(0);
			return ans;
		}
		// Single card
		if(pre.size() == 1) {
			return beatSingle(playerCard, pre);
		}
		// Double card
		if(pre.size() == 2) {
			return beatDouble(playerCard, pre);
		}
		//Triple card
		if(rule.isTriple(pre)) {
			return beatTriple(playerCard, pre);
		}
		//Four seasons
		if(rule.isFourOfAKind(pre)) {
			return beatFourSeasons(playerCard, pre);
		}
		//Sequence of card
		if(rule.isStraight(pre)) {
			return beatSequence(playerCard, pre);
		}
		//Consecutive pairs
		else {
			return beatConsecutivePairs(playerCard, pre);
		}	
	}
	
	private void deleteCard(ArrayList<Card> playerCard, ArrayList<Card> chosenCard) {
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
	
	private ArrayList<Card> findConsecutivePairs(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		int len = pre.size() == 1 ? 6 : pre.size();
		
		for(int h = 0; h <= playerCard.size() - len; h++) {
			if(playerCard.get(h).getCost() != playerCard.get(h+1).getCost()) {
				continue;
			}
			ans.add(playerCard.get(h));
			ans.add(playerCard.get(h+1));
			int i = h + 2, j = i + 1;
			while(j < playerCard.size()) {
				if(playerCard.get(i).getCost() == playerCard.get(j).getCost()) {
					if(ans.get(ans.size()-1).getCost() + 1 < playerCard.get(i).getCost()) {
						break;
					}
					
					else if(ans.get(ans.size()-1).getCost() + 1 == playerCard.get(i).getCost()) {
						ans.add(playerCard.get(i));
						ans.add(playerCard.get(j));
					}
				}
				i++;
				j++;
				if(rule.canBeat(pre, ans)) {
					deleteCard(playerCard, ans);
					return ans;
				}
			}
			ans.clear();
		}
		
		return null;
	}
	
	private ArrayList<Card> beatSingle(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		int len = playerCard.size();
		for(int i = 0; i < len; i++) {
			ans.add(playerCard.get(i));
			if(rule.canBeat(pre, ans)) {
				playerCard.remove(i);
				return ans;
			}
			else {
				ans.clear();
			}
		}

		if(pre.get(0).getCost() == 15) {
			// Chat 2 bang doi thong
			ans = findConsecutivePairs(playerCard, pre);
			if(ans != null) {
				return ans;
			}
			
			// Chat 2 bang tu quy
			for(int i = 0; i < len - 3; i++) {
				ans = new ArrayList<>(playerCard.subList(i, i + 4));
				if (rule.isFourOfAKind(ans)) {
					deleteCard(playerCard, ans);
					return ans;
				}
			}
		}
		
		return null;
	}
	
	private ArrayList<Card> beatDouble(ArrayList<Card> playerCard, ArrayList<Card> pre){
		for(int i = 0; i <= playerCard.size() - 2; i++) {
			ArrayList<Card> candidate = new ArrayList<>(playerCard.subList(i, i + 2));
			if (rule.canBeat(pre, candidate)) {
				deleteCard(playerCard, candidate);
				return candidate;
			}
		}
		
		ArrayList<Card> ans = new ArrayList<Card>();
		if(pre.get(0).getCost() == 15) {
			// Chat dot 2 bang tu quy
			for(int i = 0; i < playerCard.size() - 3; i++) {
				ans = new ArrayList<>(playerCard.subList(i, i + 4));
				if (rule.isFourOfAKind(ans)) {
					deleteCard(playerCard, ans);
					return ans;
				}
			}
			
			//Chat doi 2 bang 5 doi thong
			ans = findConsecutivePairs(playerCard, pre);
			if(ans != null &&  ans.size() == 10) {
				return ans;
			}
		}
		return null;
	}
	
	private ArrayList<Card> beatTriple(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans;
		for(int i = 0; i < playerCard.size() - 2; i++) {
			ans = new ArrayList<>(playerCard.subList(i, i + 3));
			if (rule.isTriple(ans) && rule.canBeat(pre, ans)) {
				deleteCard(playerCard, ans);
				return ans;
			}
		}
		
		return null;
	}
	
	private ArrayList<Card> beatSequence(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		int len = pre.size();
		for(int i = 0; i <= playerCard.size() - len; i++) {
			ans.add(playerCard.get(i));
			int idx = i + 1;
			while(idx < playerCard.size()) {
				if(playerCard.get(idx).getCost() > ans.get(ans.size() - 1).getCost() + 1) {
					break;
				}
				else if(playerCard.get(idx).getCost() == ans.get(ans.size() - 1).getCost() + 1) {
					ans.add(playerCard.get(idx));
				}
				idx++;
				
				if(rule.canBeat(pre, ans)) {
					deleteCard(playerCard, ans);
					return ans;
				}
			}
			ans.clear();
		}
		
		return null;
	}
	
	private ArrayList<Card> beatConsecutivePairs(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = findConsecutivePairs(playerCard, pre);
		if(ans != null) {
			return ans;
		}
		
		// Tu quy chat 3 doi thong hoac 4 doi thong
		if(pre.size() <= 8) {
			for(int i = 0; i < playerCard.size() - 3; i++) {
				ans = new ArrayList<>(playerCard.subList(i, i + 4));
				if (rule.isFourOfAKind(ans)) {
					deleteCard(playerCard, ans);
					return ans;
				}
			}
		}
		return null;
	}
	
	private ArrayList<Card> beatFourSeasons(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		// Tu quy chat bang tu quy
		for(int i = 0; i < playerCard.size() - 3; i++) {
			ans = new ArrayList<>(playerCard.subList(i, i + 4));
			if (rule.isFourOfAKind(ans)) {
				deleteCard(playerCard, ans);
				return ans;
			}
		}
		// Chat tu quy bang 5 doi thong
		ans = findConsecutivePairs(playerCard, pre);
		if(ans != null && ans.size() == 10) {
			return ans;
		}
		return null;
	}
}