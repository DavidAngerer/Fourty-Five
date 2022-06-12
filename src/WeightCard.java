import java.util.HashMap;
import java.util.Map;

public class WeightCard extends Card{

    public enum CardName{
        Remorse, Soul_Weight, Scar
    }
    CardName cardName;

    String cardDeskription;

    public WeightCard(CardName cardName, String cardDeskription) {
        this.cardName = cardName;
        this.cardDeskription = cardDeskription;
    }

    public CardName getCardName() {
        return cardName;
    }

    public void setCardName(CardName cardName) {
        this.cardName = cardName;
    }

    public String getCardDeskription() {
        return cardDeskription;
    }

    public void setCardDeskription(String cardDeskription) {
        this.cardDeskription = cardDeskription;
    }

    @Override
    public Map<String, String> getStats() {
        Map<String,String> erg= new HashMap<>();
        erg.put("Name",cardName.toString().replace("_"," "));
        erg.put("Deskription",cardDeskrition);
        return erg;
    }
}
