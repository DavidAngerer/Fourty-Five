import java.util.ArrayList;

public class Player {
    private ArrayList<Bullet> bullets;

    private ArrayList<EfectCard> efectCards;
    private ArrayList<Efect> efects;

    int health;

    int energy = 5;

    ArrayList<Card> handCards = new ArrayList<>();

    ArrayList<Bullet> bulletsInChamber = new ArrayList<>();

    int coins;


    public Player(ArrayList<Bullet> bullets,ArrayList<EfectCard> efectCards, ArrayList<Efect> efects, int health) {
        this.bullets = bullets;
        this.efectCards = efectCards;
        this.efects = efects;
        this.health = health;
        coins = 0;
    }

    public boolean addCard(Card card){
        if(card.getClass().getSimpleName().equals("Bullet")){
            return bullets.add((Bullet) card);
        }
        return efectCards.add((EfectCard) card);
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
        ArrayList cards = new ArrayList<>();
        cards.addAll(bullets);
        cards.addAll(efectCards);
        return cards;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public ArrayList<EfectCard> getEfectcards(){
        return efectCards;
    }

    public ArrayList<Bullet> getBulletsInChamber() {
        return bulletsInChamber;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
