import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class EfectCard extends Card{

    public enum EffectCardName{
        avoid,molotov,superhuman_reflexes_avoid,sweet_death,whiskey,questioning_choices,letting_luck_choose,
        aim_for_the_head,godlike_reflexes,confirmed_headshot,admirable_aim,blood_will_paint_the_rivers_red,
        sacrificial_avoid,flush,six_feet_under,your_weakness_disgusts_me,dual_wield,poison_vile,last_stand,round_skip,
        suspicious_accuracy,trying_does_not_hurt,reversed_turn,gonna_keep_this,random_Bullshit_go
    };

    protected int cost;
    private EffectCardName cardName;
    private String type;

    public EfectCard(String cardDeskrition, Rarity rarity, int cost, EffectCardName cardName,String type) {
        this.cardDeskrition = cardDeskrition;
        this.rarity = rarity;
        this.cost = cost;
        this.cardName = cardName;
        this.type = type;
    }

    public EfectCard(String[] attributes){
        cardName = EffectCardName.valueOf(attributes[0]);
        cardDeskrition = attributes[7];
        type=attributes[6];
        rarity = Rarity.valueOf(attributes[4]);
        try{
            cost = Integer.parseInt(attributes[3]);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

    }

    public EfectCard cloneEfectcard(){
        return new EfectCard(cardDeskrition,this.rarity,cost,cardName,type);
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getCardNameAsString() {
        return cardName.name().replace("_"," ");
    }

    @Override
    public Map<String, String> getStats() {
        Map<String,String> erg= new HashMap<>();
        erg.put("Name",cardName.toString());
        erg.put("Cost",cost+"");
        erg.put("Deskription",cardDeskrition);
        return erg;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getCost() {
        return cost;
    }

    public EffectCardName getCardName() {
        return cardName;
    }

    public String getType() {
        return type;
    }
}
