import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class Bullet extends Card{

    boolean damageCalculatet;

    private int damage;

    public enum BulletEffect{
        NOTHING,EVERLASTING,SPRAY,UNDEAD,GOOD_WILL,MONEY
    }

    BulletEffect bulletEffect=BulletEffect.NOTHING;

    private boolean everLasting=false;

    private boolean spray=false;

    public Bullet(CardName cardName) {
        this.cardName = cardName;
    }

    public Bullet(CardName cardName, boolean damageCalculatet, int damage, String cardDeskrition,Rarity rarity) {
        this.cardName = cardName;
        this.damageCalculatet = damageCalculatet;
        this.damage = damage;
        this.cardDeskrition = cardDeskrition;
        this.rarity = rarity;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Bullet(String[] attributes){
        cardName = CardName.valueOf(attributes[0]);
        rarity = Rarity.valueOf(attributes[3]);
        cardDeskrition = attributes[2];
        try{
            damage = Integer.parseInt(attributes[1]);
            damageCalculatet = false;
        }catch (NumberFormatException e){
            damageCalculatet = true;
            damage = 0;
        }

    }

    public void setBulletEffect(BulletEffect bulletEffect){
        this.bulletEffect = bulletEffect;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Bullet cloneBullet(){
        return new Bullet(this.cardName,this.damageCalculatet,this.damage,this.cardDeskrition,this.rarity);
    }

    @Override
    public Map<String, String> getStats() {
        Map<String,String> erg= new HashMap<>();
        erg.put("Name",cardName.toString());
        erg.put("damage",damage+"");
        erg.put("Deskription",cardDeskrition);
        return erg;
    }

    public int getDamage() {
        return damage;
    }

    public void addDamage(int amount){
        damage+=amount;
    }

    public boolean isEverLasting() {
        return everLasting;
    }

    public void setEverLasting(boolean everLasting) {
        this.everLasting = everLasting;
    }

    public boolean hasSpray() {
        return spray;
    }

    public void setSpray(boolean spray) {
        this.spray = spray;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
