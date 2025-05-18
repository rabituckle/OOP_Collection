package model.utilities.card;


public class CardBaCay extends BasePlayingCard {


    public CardBaCay(String suit, String rank) {
        super(suit, rank);
    }

    @Override
    protected String[] getRANKS() {
        return CardRank.BA_CAY_RANKS;
    }

}

