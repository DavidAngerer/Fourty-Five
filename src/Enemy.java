public class Enemy {
    int health;

    int maxHealth;
    int damage;
    Efect efect;

    String name;

    public Enemy(int health, int damage, Efect efect) {
        this.health = health;
        this.damage = damage;
        this.efect = efect;
        maxHealth = health;
    }
}
