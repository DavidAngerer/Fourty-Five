import javafx.scene.Node;

import java.util.Map;

public abstract class Card {

    protected enum Rarity{
        na,common,rare,superrare,ultrarare
    }

    protected Rarity rarity;
    protected enum CardName{
        Bullet,Bewitched_Bullet,Incendiary_Bullet,Explosive_Bullet,Leaders_Bullet,Poison_Bullet,Gamblers_Bullet,
        Medics_Bullet,Obsidian_Bullet,Rusted_Bullet,BFB,Bullet_Bullet,Arrow,Undead_Bullet,Moon_Bullet,Rotten_Bullet,
        Wealthy_Bullet,Backup_Bullet,Bullet_Bullet_Bullet,Nuclear_Bullet, Shotgun_Shell_Bullet
    }
    protected CardName cardName;

    protected String cardDeskrition;

    protected Node node;

    public String getCardNameAsString(){
        return cardName.name().replace("_"," ");
    }

    public abstract Map<String,String> getStats();

    public abstract Node getNode();
    public abstract void setNode(Node node);

    public boolean gotCard(){
        double rnd =Math.random() * 100;
        int range = 0;
        switch (rarity) {
            case common -> range = 100;
            case rare -> range = 25;
            case superrare -> range = 5;
            case ultrarare -> range = 1;
        }
        if(rnd<=range){
            return true;
        }
        return false;
    }
}
