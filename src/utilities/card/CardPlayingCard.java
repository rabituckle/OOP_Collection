package utilities.card;


public class CardPlayingCard extends BasePlayingCard {

	public static final String[] RANKS = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
	public static final String[] SUITS = {"S", "C", "D", "H"};

    public CardPlayingCard(String suit, String rank) {
        super(suit, rank);
        int index = findIndex(getRANKS(), rank);
        this.cost = (index != -1) ? index + 3 : 0;
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
