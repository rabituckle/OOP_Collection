
package utilities.card;


public class CardBaCay extends BasePlayingCard {

	public static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "A"};
	public static final String[] SUITS = {"S", "C", "H", "D"};
	
    public CardBaCay(String suit, String rank) {
        super(suit, rank);
        int index = findIndex(getRANKS(), rank);
        this.cost = (index != -1) ? index + 2 : 0;
    }

    @Override
    protected String[] getRANKS() {
        return RANKS;
    }

	@Override
	protected String[] getSUITS() {
		return SUITS;
	}

}

