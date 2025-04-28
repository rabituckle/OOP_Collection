package group17;

import java.util.ArrayList;

public class BotAITLMN {
	
	public void deleteCard(ArrayList<Card> playerCard, ArrayList<Card> chosenCard) {
		int j = 0, i = 0;
		while(i < chosenCard.size()) {
			if(chosenCard.get(i).compareTo(playerCard.get(j)) == 0) {
				playerCard.remove(j);
				i++;
			}
			else {
				j++;
			}
		}
	}
	
	public ArrayList<Card> findConsecutivePairs(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		if(pre == null) {
			int i = 0, j = i + 1;
			while(j < playerCard.size() && playerCard.get(j).getCost() < 15) {
				if(playerCard.get(i).getCost() == playerCard.get(j).getCost()) {
					if(ans.size() >= 6 && ans.get(ans.size()-1).getCost() + 1 < playerCard.get(i).getCost()) {
						deleteCard(playerCard, ans);
						return ans;
					}
					
					if(ans.size() != 0 && ans.get(ans.size()-1).getCost() + 1 < playerCard.get(i).getCost()) {
						ans.clear();
					}
					ans.add(playerCard.get(i));
					ans.add(playerCard.get(j));
					i = j + 1;
					j = i + 1;
				}
				else {
					i++;
					j++;
				}
			}
			if(ans.size() < 6) {
				return null;
			}
			deleteCard(playerCard, ans);
			return ans;
		}
		
		int i = 0;
		while(playerCard.get(i).getCost() < pre.get(0).getCost()) {
			i++;
			if(i == playerCard.size() || playerCard.get(i).getCost() == 15) {
				return null;
			}
		}
			
		int j = i + 1;
		while(j < playerCard.size() && playerCard.get(j).getCost() < 15) {
			if(playerCard.get(i).getCost() == playerCard.get(j).getCost()) {
				if(ans.size() >= 6 && ans.get(ans.size()-1).getCost() + 1 < playerCard.get(i).getCost()) {
					if(ans.get(ans.size()-1).compareTo(pre.get(pre.size()-1)) > 0) {
						deleteCard(playerCard, ans);
						return ans;
					}
				}
					
				if(ans.size() != 0 && ans.get(ans.size()-1).getCost() + 1 < playerCard.get(i).getCost()) {
					ans.clear();
				}
				ans.add(playerCard.get(i));
				ans.add(playerCard.get(j));
				i = j + 1;
				j = i + 1;
			}
			else {
				i++;
				j++;
			}
		}
		if(ans.size() < pre.size()) {
			return null;
		}
		if(ans.get(ans.size()-1).compareTo(pre.get(pre.size()-1)) < 0) {
			return null;
		}
		deleteCard(playerCard, ans);
		return ans;
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
			return engineSingle(playerCard, pre);
		}
		// Double card
		if(pre.size() == 2) {
			return engineDouble(playerCard, pre);
		}
		//Triple card
		if(pre.size() == 3 && pre.get(0).getCost() == pre.get(1).getCost()) {
			return engineTriple(playerCard, pre);
		}
		//Four seasons
		if(pre.size() == 4 && pre.get(0).getCost() == pre.get(3).getCost()) {
			return engineFourSeasons(playerCard, pre);
		}
		//Sequence of card
		if(pre.size() >= 3 && pre.get(0).getCost() + 1 == pre.get(1).getCost()) {
			return engineSequence(playerCard, pre);
		}
		//Consecutive pairs
		else {
			return engineConsecutivePairs(playerCard, pre);
		}
		
	}
	
	public ArrayList<Card> engineSingle(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		int len = playerCard.size();
		for(int i = 0; i < len; i++) {
			if(playerCard.get(i).compareTo(pre.get(0)) > 0) {
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				return ans;
			}
		}
		// Chat 2 bang doi thong
		if(pre.get(0).getCost() == 15) {
			ans = findConsecutivePairs(playerCard, null);
			if(ans != null) {
				return ans;
			}
		}
		// Chat 2 bang tu quy
		ans = new ArrayList<Card>();
		for(int i = 0; i < len - 3; i++) {
			if(playerCard.get(i).getCost() == playerCard.get(i+3).getCost()) {
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				return ans;
			}
		}
		
		return null;
	}
	
	public ArrayList<Card> engineDouble(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		int i = 0;
		while(playerCard.get(i).getCost() < pre.get(0).getCost()) {
			i++;
			if(i == playerCard.size()) {
				break;
			}
		}
		
		while(i < playerCard.size() - 1) {
			if(playerCard.get(i).getCost() == playerCard.get(i+1).getCost()) {
				if(playerCard.get(i+1).compareTo(pre.get(1)) > 0) {
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					return ans;
				}
			}
			i++;
		}
		
		if(pre.get(0).getCost() == 15) {
			// Chat dot 2 bang tu quy
			for(i = 0; i < playerCard.size() - 3; i++) {
				if(playerCard.get(i).getCost() == playerCard.get(i+3).getCost()) {
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					return ans;
				}
			}
			
			//Chat doi 2 bang 5 doi thong
			ans = findConsecutivePairs(playerCard, null);
			if(ans != null &&  ans.size() == 10) {
				return ans;
			}
		}
		return null;
	}

	public ArrayList<Card> engineTriple(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		for(int i = 0; i < playerCard.size() - 2; i++) {
			if(playerCard.get(i).getCost() == playerCard.get(i+2).getCost() && playerCard.get(i).getCost() > pre.get(0).getCost()) {
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				return ans;
			}
		}
		
		return null;
	}
	
	public ArrayList<Card> engineSequence(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		int len = pre.size();
		int i = 0;
		while(playerCard.get(i).compareTo(pre.get(0)) < 0) {
			i++;
			if(i == playerCard.size() || playerCard.get(i).getCost() == 15) {
				return null;
			}
		}
		while(i < playerCard.size() && playerCard.get(i).getCost() < 15) {
			if(ans.size() == 0) {
				ans.add(playerCard.get(i));
			}
			else if(ans.get(ans.size()-1).getCost() + 1 < playerCard.get(i).getCost()) {
				ans.clear();
				ans.add(playerCard.get(i));
			}
			else if(ans.get(ans.size()-1).getCost() + 1 == playerCard.get(i).getCost()) {
				ans.add(playerCard.get(i));
			}
			if(ans.size() == len) {
				if(ans.get(len-1).compareTo(pre.get(len-1)) > 0) {
					deleteCard(playerCard, ans);
					return ans;
				}
				else {
					ans.remove(0);
				}
			}
			i++;
		}
		return null;
	}
	
	public ArrayList<Card> engineConsecutivePairs(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = findConsecutivePairs(playerCard, pre);
		if(ans != null) {
			return ans;
		}
		
		ans = new ArrayList<Card>();
		// Tu quy chat 3 doi thong hoac 4 doi thong
		if(pre.size() <= 8) {
			for(int i = 0; i < playerCard.size() - 3; i++) {
				if(playerCard.get(i).getCost() == playerCard.get(i+3).getCost()) {
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					ans.add(playerCard.get(i));
					playerCard.remove(i);
					return ans;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Card> engineFourSeasons(ArrayList<Card> playerCard, ArrayList<Card> pre){
		ArrayList<Card> ans = new ArrayList<Card>();
		// Tu quy chat bang tu quy
		for(int i = 0; i < playerCard.size() - 3; i++) {
			if(playerCard.get(i).getCost() == playerCard.get(i+3).getCost() && playerCard.get(i).getCost() > pre.get(0).getCost()) {
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
				ans.add(playerCard.get(i));
				playerCard.remove(i);
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