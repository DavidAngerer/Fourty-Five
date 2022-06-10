import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private ArrayList<Bullet> bullets;

    private ArrayList<EfectCard> efectCards;
    private ArrayList<Efect> efects;

    int health;

    private int maxHealth;

    int energy = 5;

    ArrayList<Card> handCards = new ArrayList<>();

    Bullet[] bulletsInChamber = new Bullet[5];

    int coins;
    double avoidChance=0;


    public Player(ArrayList<Bullet> bullets,ArrayList<EfectCard> efectCards, ArrayList<Efect> efects, int health) {
        this.bullets = bullets;
        this.efectCards = efectCards;
        this.efects = efects;
        this.health = health;
        maxHealth = health;
        Arrays.fill(bulletsInChamber,null);
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

    public Bullet[] getBulletsInChamber() {
        return bulletsInChamber;
    }

    public void setBulletInChamber(Bullet bullet, int pos){
        bulletsInChamber[pos] = bullet;
    }

    public void removeBullet(int pos){
        bulletsInChamber[pos]=null;
    }

    public void removeBullet(Bullet bullet){
        for (int i = 0; i < bulletsInChamber.length; i++) {
            if(bulletsInChamber[i].equals(bullet)){
                bulletsInChamber[i]=null;
            }
        }
    }

    public int setBulletInFirstAvailableChamber(Bullet bullet){
        if(getFirstAvailableSlot()==-1){
            return -1;
        }
        bulletsInChamber[getFirstAvailableSlot()]=bullet;
        return getFirstAvailableSlot();
    }

    public boolean bulletsInChamberContain(Bullet bullet){
        for (int i = 0; i < bulletsInChamber.length; i++) {
            if(bulletsInChamber[i]!=null && bulletsInChamber[i].equals(bullet)){
                return true;
            }
        }
        return false;
    }

    public int getFirstAvailableSlot(){
        for (int i = 0; i < bulletsInChamber.length; i++) {
            if(bulletsInChamber[i]==null){
                return i;
            }
        }
        return -1;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        main.setLife(health);
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
