import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class Bullet extends Card{

    boolean damageCalculatet;

    private int damage;

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
        //cardDeskrition = attributes[5];
        try{
            damage = Integer.parseInt(attributes[1]);
            damageCalculatet = false;
        }catch (NumberFormatException e){
            damageCalculatet = true;
            damage = 0;
        }

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

    public String getCardNameAsString() {
        return cardName.name().replace("_"," ");
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
}
