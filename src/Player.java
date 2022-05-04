import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cards;
    private ArrayList<Efect> efects;

    int health;

    int coins;


    public Player(ArrayList<Card> cards, ArrayList<Efect> efects, int health) {
        this.cards = cards;
        this.efects = efects;
        this.health = health;
        coins = 0;
    }

    public boolean addCard(Card card){

        return cards.add(card);
    }

    public void addEfect(Efect efect){
        for (int i = 0; i < efects.size(); i++) {
            if(efects.get(i).efectName == efect.efectName){
                efects.get(i).addCycles(efect.cyclesLeft);
            }
        }
        efects.add(efect);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Bullet> getBullets(){
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if(cards.get(i).getClass().getSimpleName().equals("Bullet")){
                bullets.add((Bullet) cards.get(i));
            }
        }
        return bullets;
    }

    public ArrayList<EfectCard> getEfectcards(){
        ArrayList<EfectCard> efectCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if(!cards.get(i).getClass().getSimpleName().equals("Bullet")){
                efectCards.add((EfectCard) cards.get(i));
            }
        }
        return efectCards;
    }
}
