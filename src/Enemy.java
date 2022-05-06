public class Enemy {
    private int health;

    private int maxHealth;
    private int damage;
    private Efect efect;

    private String name;

    public Enemy(int health, int damage, Efect efect) {
        this.health = health;
        this.damage = damage;
        this.efect = efect;
        maxHealth = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Efect getEfect() {
        return efect;
    }

    public void setEfect(Efect efect) {
        this.efect = efect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
