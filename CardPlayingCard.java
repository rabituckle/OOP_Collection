package model.utilities.card;


public class CardPlayingCard extends BasePlayingCard {


    public CardPlayingCard(String suit, String rank) {
        super(suit, rank);
    }

    @Override
    protected String[] getRANKS() {
        return CardRank.TIEN_LEN_RANKS;
    }

}

