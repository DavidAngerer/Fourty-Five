public class Bullet extends Card{

    private CardName cardName;

    boolean damageCalculatet;
    int damage;

    float rarity;

    String cardDeskrition;

    public Bullet(CardName cardName) {
        this.cardName = cardName;
    }

    public Bullet(CardName cardName, boolean damageCalculatet, int damage, String cardDeskrition) {
        this.cardName = cardName;
        this.damageCalculatet = damageCalculatet;
        this.damage = damage;
        this.cardDeskrition = cardDeskrition;
    }

    public Bullet(String[] attributes){
        cardName = CardName.valueOf(attributes[0]);
        //cardDeskrition = attributes[5];
        try{
            damage = Integer.parseInt(attributes[1]);
            damageCalculatet = false;
        }catch (NumberFormatException e){
            damageCalculatet = true;
            damage = 0;
        }

    }
}
