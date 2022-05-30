import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Controller {
    private ArrayList<Enemy> enemiesThisTurn;
    float dificulty = 1;
    int stage = 1;

    Player player;

    ArrayList<Bullet> bulletsinExistence;

    ArrayList<EfectCard> efectCardsInExistence;

    ArrayList<Efect> efectsInExistence;

    ArrayList<Card> cardsOnField = new ArrayList<>();

    float headShotProbability = 10;

    private boolean [] headShotThisTurn = new boolean[]{false,false,false};

    private int freemoves = 0;

    private boolean dualWield = false;

    public Controller(float dificulty, int stage) {
        bulletsinExistence = new ArrayList<>();
        efectsInExistence = new ArrayList<>();
        efectCardsInExistence = new ArrayList<>();
        this.dificulty = dificulty;
        this.stage = stage;
        player = new Player(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 100);
        fillCards();
        fillEfects();
        for (int i = 0; i < 5; i++) {
            player.addCard(bulletsinExistence.get(0).cloneBullet());
            player.addCard(efectCardsInExistence.get(0).cloneEfectcard());
        }
        player.addCard(bulletsinExistence.get(1).cloneBullet());
        player.addCard(efectCardsInExistence.get(1).cloneEfectcard());
    }

    public void nextStage() {
        stage++;
        int hpPool = (int) (Math.random() * (stage * 5)) + (stage * 15) + 50;
        int damage = (int) (Math.random() * (stage * 2)) + (stage * 2);
        enemiesThisTurn = new ArrayList<>();
        int enemyNumbers = 3;//(int)(Math.random()*3)+1;
        player.bulletsInChamber = new ArrayList<>();
        player.handCards=new ArrayList<>();
        player.setAvoidChance(0);
        freemoves=0;
        dualWield=false;
        cardsOnField = new ArrayList<>();
        headShotThisTurn= new boolean[]{false, false,false};
        for (int i = 0; i < enemyNumbers; i++) {
            Efect effekt = efectsInExistence.get((int) (efectsInExistence.size() * Math.random()));
            enemiesThisTurn.add(new Enemy(hpPool / enemyNumbers, damage, effekt));
        }
        main.newStage(stage, player.getMaxHealth(), enemiesThisTurn);
    }

    public void enemiesTurn() {
        float multiplier = 1;
        if(freemoves<=0){
            for (Enemy enemy :
                    enemiesThisTurn) {
                if(Math.random()>player.getAvoidChance()){
                    if(enemy.hasEfect(Efect.EfectName.WEAK)){
                        multiplier*=0.5;
                    }
                    if(player.hasEffect(Efect.EfectName.THORNES)){
                        enemy.setHealth((int)(enemy.getHealth()-enemy.getDamage()*multiplier));
                        main.setLifeOfEnemy(enemy);
                        if (enemy.getHealth() <= 0) {
                            main.removeEnemy(enemy);
                            enemiesThisTurn.remove(enemy);
                        }
                    }
                    player.setHealth((int)(player.getHealth() - enemy.getDamage()*multiplier));
                    if (enemy.getEfect() != null) {
                        player.addEfect(enemy.getEfect());
                    }
                }
            }
        }
        if(checkAlive()){
            nextTurn();
        }
    }

    private void nextTurn() {
        player.setAvoidChance(0);
        doEfects();
        headShotThisTurn= new boolean[]{false, false,false};
        int cardsLeftToDraw = 6 - main.handcardsTaken;
        if(freemoves>0){
            freemoves--;
        }
        int rnd;
        for (int i = 0; i < cardsLeftToDraw; i++) {
            rnd = (int) (Math.random() * 2);
            if (rnd == 0) {
                rnd = (int) (Math.random() * player.getBullets().size());
                if (!cardsOnField.contains(player.getBullets().get(rnd))) {
                    main.addCardInHand(player.getBullets().get(rnd));
                } else {
                    i--;
                }
            } else {
                rnd = (int) (Math.random() * player.getEfectcards().size());
                if (!cardsOnField.contains(player.getEfectcards().get(rnd))) {
                    main.addCardInHand(player.getEfectcards().get(rnd));
                } else {
                    i--;
                }
            }
        }
        player.energy = 5;
        main.setEnergy(5);
    }

    public boolean checkAlive(){
        main.setLife(player.getHealth());
        if (player.getHealth() <= 0) {
            main.deathScreen();
            return false;
        }
        return true;
    }

    public void doEfects(){
        if(player.hasEffect(Efect.EfectName.BURN)){
            player.setHealth(player.getHealth()-5);
        }
        checkAlive();
    }

    private void fillCards() {
        Path path = Path.of("./res/CardData/Bullet.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                bulletsinExistence.add(new Bullet(attributes));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        path = Path.of("./res/CardData/CardData.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                StandardCharsets.US_ASCII)) {
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                efectCardsInExistence.add(new EfectCard(attributes));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<EfectCard> getRndEffectCards(int amount) {
        ArrayList<EfectCard> efectCard = player.getEfectcards();
        ArrayList<EfectCard> erg = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int number = (int) (Math.random() * efectCard.size());
            while (player.handCards.contains(efectCard.get(number))) {
                number = (int) (Math.random() * efectCard.size());
            }
            player.handCards.add(efectCard.get(number));
            erg.add(efectCard.get(number));
        }
        return erg;
    }

    public boolean isHead(){
        if(Math.random()*2<1){
            return true;
        }
        return false;
    }

    public void useEffectCard(EfectCard card) {
        if (card.getCost() <= player.getEnergy()) {
            if(card.getType()!=null &&card.getType().equals("avoid")){
                avoidCards(card);
            }else if(card.getType()!=null &&card.getType().equals("direct dmg")){
                directdamage(card);
            }else if(card.getType()!=null &&card.getType().equals("headshot")){
                headShot(card);
            }else if(card.getType()!=null &&card.getType().equals("kill")){
                kill(card);
            }else if(card.getType()!=null &&card.getType().equals("other")){
                other(card);
            }else if(card.getType()!=null &&card.getType().equals("turn")){
                turn(card);
            }
            cardsOnField.remove(card);
            main.removeCard(card);
            player.setEnergy(player.getEnergy() - card.getCost());
            main.setEnergy(player.getEnergy());
        }
    }

    private void turn(EfectCard card){
        switch ((card.getCardName())){
            case round_skip ->{
                main.TurnRight();
                Bullet bulletToRemove = player.getBulletsInChamber().get(player.getBulletsInChamber().size()-1);
                player.getBulletsInChamber().remove(bulletToRemove);
                player.getBulletsInChamber().add(0,
                        bulletToRemove);
            }
            case reversed_turn -> {
                main.TurnLeft();
                Bullet bulletToRemove = player.getBulletsInChamber().get(0);
                player.getBulletsInChamber().remove(bulletToRemove);
                player.getBulletsInChamber().add(bulletToRemove);
            }
        }
    }

    private void other(EfectCard card){
        switch (card.getCardName()){
            case questioning_choices -> {
                for (int i = main.handcardsTaken; i < 6; i++) {
                    cardsOnField.add(player.bulletsInChamber.get(0));
                    main.putCardWhereGoes(player.bulletsInChamber.get(0));
                    player.getBulletsInChamber().remove(0);
                    main.rotate();
                }
                for (int i = 0; i < player.getBulletsInChamber().size(); i++) {
                    main.rotate();
                    player.getBulletsInChamber().remove(0);
                }
            }
            case letting_luck_choose -> {
                boolean isHead = isHead();
                main.coinflip(isHead);
                if(isHead){
                    freemoves+=2;
                }else{
                    if(player.getHealth()+10<player.getMaxHealth()){
                        player.setHealth(player.getHealth()+10);
                    }else{
                        player.setHealth(player.getMaxHealth());
                    }
                }
            }
            case dual_wield -> {
                dualWield = true;
            }
        }
    }

    public int getMaxHealth(){
        return player.getMaxHealth();
    }

    private void kill(EfectCard card){
        switch (card.getCardName()){
            case six_feet_under -> {
                for (Enemy enemy:
                     enemiesThisTurn) {
                    if(enemy.getEfectsOnHim().size()>3){
                        main.removeEnemy(enemiesThisTurn.get(0));
                        enemiesThisTurn.remove(enemiesThisTurn.get(0));
                    }
                }
            }
            case poison_vile -> {
                enemiesThisTurn.get(0).addEfectOnHim(new Efect(Efect.EfectName.POISOND,6,true));
            }case last_stand -> {
                player.addEfect(new Efect(Efect.EfectName.POISOND,5,true));
            }
        }
        if(enemiesThisTurn.size()==0){
            main.cardSelectScreen();
        }
    }

    private void headShot(EfectCard card){
        switch (card.getCardName()){
            case aim_for_the_head -> headShotThisTurn[1]=true;
            case confirmed_headshot -> headShotProbability=360;
            case admirable_aim -> {
                if(headShotThisTurn[0]){
                    player.setMaxHealth(player.getMaxHealth()+3);
                    main.setLife(player.getHealth());
                }
            }
            case suspicious_accuracy -> {
                if(headShotProbability+360*0.3<=360){
                    headShotProbability+=360*0.3;
                }else{
                    headShotProbability=360;
                }
            }
            case trying_does_not_hurt -> {
                headShotThisTurn[2]=true;
            }
        }
    }

    private void directdamage(EfectCard card){
        switch (card.getCardName()){
            case sweet_death -> {
                player.setHealth((int)Math.round(player.getHealth()*0.6));
                main.setLife(player.getHealth());
                enemiesThisTurn.get(0).setHealth(enemiesThisTurn.get(0).getHealth() - 60);
                main.setLifeOfEnemy(enemiesThisTurn.get(0));
                if (enemiesThisTurn.get(0).getHealth() <= 0) {
                    main.removeEnemy(enemiesThisTurn.get(0));
                    enemiesThisTurn.remove(enemiesThisTurn.get(0));
                }
            }
            case blood_will_paint_the_rivers_red -> {
                for (Enemy enemy:
                     enemiesThisTurn) {
                    enemy.setHealth(enemy.getHealth()-20);
                    main.setLifeOfEnemy(enemy);
                    if (enemy.getHealth() <= 0) {
                        main.removeEnemy(enemy);
                        enemiesThisTurn.remove(enemy);

                    }
                }
            }
        }if(enemiesThisTurn.size()==0){
            main.cardSelectScreen();
        }
    }

    private void avoidCards(EfectCard card){
        switch (card.getCardName()){
            case avoid -> player.setAvoidChance(player.getAvoidChance()+0.1);
            case superhuman_reflexes_avoid -> player.setAvoidChance(player.getAvoidChance()+0.2);
            case godlike_reflexes -> player.setAvoidChance(player.getAvoidChance()+1);
            case sacrificial_avoid -> {
                player.setAvoidChance(((double)(cardsOnField.size()-cardsOnField.stream().filter(n -> n.getClass().
                        getSimpleName().equals("EfectCard")).
                        filter(n -> ((EfectCard)n).getType().equals("avoid")).count()))/10);
                for (Card card1:
                cardsOnField.stream().filter(n -> n.getClass().getSimpleName().equals("Bullet") ||
                        !((EfectCard)n).getType().equals("avoid")).collect(Collectors.toList())) {
                    main.removeCard(card1);
                }
                cardsOnField.removeIf(n -> n.getClass().getSimpleName().equals("Bullet") ||
                        !((EfectCard)n).getType().equals("avoid"));
            }
        }
    }

    public void shoot(Enemy enemy, boolean body) {
        float head = 1;
        if(player.hasEffect(Efect.EfectName.WEAK)){
            head*=0.5;
        }
        if (!body) {
            head *= 2;
            headShotThisTurn[0]=true;
            headShotProbability=10;
        }
        if(headShotThisTurn[1]&&headShotThisTurn[0]){
            head*=2;
        }
        enemy.setHealth((int)(enemy.getHealth() -
                player.bulletsInChamber.get(0).getDamage() * head));
        main.setLifeOfEnemy(enemy);
        if (enemy.getHealth() <= 0) {
            main.removeEnemy(enemy);
            enemiesThisTurn.remove(enemy);

        }if(enemiesThisTurn.size()==0){
            main.cardSelectScreen();
        }else{
            player.energy--;
            main.setEnergy(player.getEnergy());
            if(dualWield){
                dualWield=false;
            }else{
                main.rotate();
                cardsOnField.remove(player.bulletsInChamber.get(0));
                player.bulletsInChamber.remove(0);
            }
        }
    }

    public void miss(Enemy enemy){
        if(headShotThisTurn[2]){
            shoot(enemy,true);
        }else{
            player.energy--;
            main.setEnergy(player.getEnergy());
            main.rotate();
            cardsOnField.remove(player.bulletsInChamber.get(0));
            player.bulletsInChamber.remove(0);
            if(headShotProbability+5<=360){
                headShotProbability+=5;
            }
        }
    }

    public Card[] getCardsToSelect(){
        ArrayList<Card> allCards = new ArrayList<>(bulletsinExistence);
        allCards.addAll(efectCardsInExistence);
        Card[] cards = new Card[3];
        for (int i = 0; i < 3; i++) {
            int rnd = (int) (Math.random() * allCards.size());
            while (!allCards.get(rnd).gotCard() &&
                    Arrays.stream(cards).collect(Collectors.toList()).contains(allCards.get(rnd))){
                rnd = (int) (Math.random() * allCards.size());
            }
            cards[i] = cloneCard(allCards.get(rnd));
        }
        return cards;
    }

    public ArrayList<Bullet> getRndBullets(int amount) {
        ArrayList<Bullet> bullets = player.getBullets();
        ArrayList<Bullet> erg = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int number = (int) (Math.random() * bullets.size());
            while (player.bulletsInChamber.contains(bullets.get(number)) ||
                    player.handCards.contains(bullets.get(number))) {
                number = (int) (Math.random() * bullets.size());
            }
            player.handCards.add(bullets.get(number));
            erg.add(bullets.get(number));
        }
        return erg;
    }

    private void fillEfects() {
        for (Efect.EfectName name :
                Efect.EfectName.values()) {
            efectsInExistence.add(new Efect(name));
        }
    }

    public int chambersTaken() {
        return player.bulletsInChamber.size();
    }

    public void setBulletInSlot(Bullet bullet) {
        player.bulletsInChamber.add(bullet);
    }

    public ArrayList<Bullet> getPlayersBullet() {
        return player.getBulletsInChamber();
    }

    public ArrayList<Enemy> getEnemiesThisTurn() {
        return enemiesThisTurn;
    }

    public int getEnergy() {
        return player.getEnergy();
    }

    public static Card cloneCard(Card card){
        if(card.getClass().getSimpleName().equals("Bullet")){
            Bullet bullet = (Bullet) card;
            return bullet.cloneBullet();
        }
        EfectCard efectCard = (EfectCard) card;
        return efectCard.cloneEfectcard();
    }

    public void addCardToPlayer(Card card){
        player.addCard(card);
    }

    public float getHeadShotProbability() {
        return headShotProbability;
    }

    public void setHeadShotProbability(float headShotProbability) {
        this.headShotProbability = headShotProbability;
    }
}
