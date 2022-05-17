import javafx.scene.Node;

import java.util.Map;

public abstract class Card {

    protected enum Rarity{
        na,common,rare,superrare,ultrarare
    }

    protected Rarity rarity;
    protected enum CardName{
        Bullet,Bewitched_Bullet,Incendiary_Bullet,Explosive_Bullet,Leaders_Bullet,Poison_Bullet,Gamblers_Bullet,
        Medics_Bullet,Obsidian_Bullet,Rusted_Bullet,BFB,Bullet_Bullet,Arrow,undead_bullet,moon_Bullet,rotten_Bullet,
        weathy_bullet,Backup_Bullet,Bullet_Bullet_Bullet,Nuclear_Bullet,shotgun_shell_Bullet
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
}
