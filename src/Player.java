import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cards;
    private ArrayList<Efect> efects;

    int health;


    public Player(ArrayList<Card> cards, ArrayList<Efect> efects, int health) {
        this.cards = cards;
        this.efects = efects;
        this.health = health;
    }

    public boolean addCard(Card card){
        for (int i = 0; i < cards.size(); i++) {
            if(cards.get(i).cardName == card.cardName){
                return false;
            }
        }
        return cards.add(card);
    }

    public boolean addEfect(Card card){
        return cards.add(card);
    }
}
