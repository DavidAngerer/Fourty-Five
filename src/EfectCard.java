public class EfectCard extends Card{

    String cardDeskrition;

    private int cost;
    private CardName cardName;

    public String getCardNameAsString() {
        return cardName.name().replace("_"," ");
    }
}
