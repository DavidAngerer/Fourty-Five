public class Bullet extends Card{

    private CardName cardName;

    int cost;
    int health;
    boolean isBullet;

    String cardDeskrition;

    public Bullet(CardName cardName) {
        this.cardName = cardName;
    }

    public Bullet(CardName cardName, int cost, int health, boolean isBullet, String cardDeskrition) {
        this.cardName = cardName;
        this.cost = cost;
        this.health = health;
        this.isBullet = isBullet;
        this.cardDeskrition = cardDeskrition;
    }
}
