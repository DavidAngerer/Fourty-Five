import java.util.ArrayList;

public class Player {
    private ArrayList<Bullet> bullets;

    private ArrayList<EfectCard> efectCards;
    private ArrayList<Efect> efects;

    int health;

    private int maxHealth;

    int energy = 5;

    ArrayList<Card> handCards = new ArrayList<>();

    ArrayList<Bullet> bulletsInChamber = new ArrayList<>();

    int coins;
    double avoidChance=0;


    public Player(ArrayList<Bullet> bullets,ArrayList<EfectCard> efectCards, ArrayList<Efect> efects, int health) {
        this.bullets = bullets;
        this.efectCards = efectCards;
        this.efects = efects;
        this.health = health;
        maxHealth = health;
        coins = 0;
    }

    public boolean addCard(Card card){
        if(card.getClass().getSimpleName().equals("Bullet")){
            return bullets.add((Bullet) card);
        }
        return efectCards.add((EfectCard) card);
    }

    public void addEfect(Efect efect){
        if(efect.efectName == Efect.EfectName.RAGE){
            efects.add(efect);
        }else{
            boolean efectInside = false;
            for (Efect efect1:
                    efects) {
                if(efect1.efectName==efect.efectName){
                    efectInside=true;
                    efect1.addCycles(efect.cyclesLeft);
                }
            }
            if(!efectInside){
                this.efects.add(efect);
            }
        }
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getAvoidChance() {
        return avoidChance;
    }

    public void setAvoidChance(double avoidChance) {
        this.avoidChance = avoidChance;
    }

    public ArrayList<Efect> getEfects() {
        return efects;
    }

    public boolean hasEffect(Efect.EfectName efectName){
        for (Efect efect:
             efects) {
            if(efect.efectName == efectName){
                return true;
            }
        }
        return false;
    }

    public int rageMulitplier(){
        int rage = 1;
        for (Efect efect:
             efects) {
            if(efect.efectName== Efect.EfectName.RAGE){
                rage++;
            }
        }
        return rage;
    }
}
