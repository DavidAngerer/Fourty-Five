public class EfectCard extends Card{

    public enum EffectCardName{
        avoid,molotov,superhuman_reflexes_avoid,sweet_death,whiskey,questioning_choices,letting_luck_choose,
        aim_for_the_head,godlike_reflexes,confirmed_headshot,admirable_aim,blood_will_paint_the_rivers_red,
        sacrificial_avoid,flush,six_feet_under,your_weakness_disgusts_me,dual_wield,poison_vile,last_stand,round_skip,
        suspicious_accuracy,trying_does_not_hurt,reversed_turn,gonna_keep_this,random_Bullshit_go
    };

    String cardDeskrition;

    boolean isOnField;

    private Rarity rarity;

    private int cost;
    private EffectCardName cardName;

    public EfectCard(String cardDeskrition, Rarity rarity, int cost, EffectCardName cardName) {
        this.cardDeskrition = cardDeskrition;
        this.rarity = rarity;
        this.cost = cost;
        this.cardName = cardName;
    }

    public EfectCard(String[] attributes){
        cardName = EffectCardName.valueOf(attributes[0]);
        cardDeskrition = attributes[7];
        rarity = Rarity.valueOf(attributes[4]);
        try{
            cost = Integer.parseInt(attributes[3]);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

    }

    public EfectCard cloneEfectcard(){
        return new EfectCard(cardDeskrition,this.rarity,cost,cardName);
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getCardNameAsString() {
        return cardName.name().replace("_"," ");
    }
}
