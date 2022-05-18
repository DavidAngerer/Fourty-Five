import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Enemy> enemiesThisTurn;
    float dificulty = 1;
    int stage = 1;

    Player player;

    ArrayList<Bullet> bulletsinExistence;

    ArrayList<EfectCard> efectCardsInExistence;

    ArrayList<Efect> efectsInExistence;

    ArrayList<Card> cardsOnField = new ArrayList<>();

    public Controller(float dificulty, int stage) {
        bulletsinExistence=new ArrayList<>();
        efectsInExistence=new ArrayList<>();
        efectCardsInExistence= new ArrayList<>();
        this.dificulty = dificulty;
        this.stage = stage;
        player = new Player(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),100);
        fillCards();
        fillEfects();
        for (int i = 0; i < 5; i++) {
            player.addCard(bulletsinExistence.get(0).cloneBullet());
            player.addCard(efectCardsInExistence.get(0).cloneEfectcard());
        }
        player.addCard(bulletsinExistence.get(1).cloneBullet());
        player.addCard(efectCardsInExistence.get(1).cloneEfectcard());
    }

    public void nextStage(){
        stage++;
        int hpPool = (int)(Math.random()*(stage*5))+(stage*15)+50;
        int damage = (int)(Math.random()*(stage*2))+(stage*2);
        enemiesThisTurn = new ArrayList<>();
        int enemyNumbers = 3;//(int)(Math.random()*3)+1;
        System.out.println(hpPool);
        System.out.println(enemyNumbers);
        for (int i = 0; i < enemyNumbers; i++) {
            Efect effekt = efectsInExistence.get((int)(efectsInExistence.size()*Math.random()));
            enemiesThisTurn.add(new Enemy(hpPool/enemyNumbers,damage,effekt));
        }
        main.newStage(stage, player.getHealth(), enemiesThisTurn);
    }

    public void enemiesTurn(){
        for (Enemy enemy:
             enemiesThisTurn) {
            player.setHealth(player.getHealth()-enemy.getDamage());
            if(enemy.getEfect()!=null){
                player.addEfect(enemy.getEfect());
            }
        }
        main.setLife(player.getHealth());
        if(player.getHealth()<=0){
            main.deathScreen();
        }else{
            nextTurn();
        }
    }

    private void nextTurn(){
        int cardsLeftToDraw = 6-main.handcardsTaken;
        int rnd;
        for (int i = 0; i < cardsLeftToDraw; i++) {
            rnd = (int)(Math.random()*2);
            if(rnd == 0){
                rnd = (int)(Math.random()*player.getBullets().size());
                if(!cardsOnField.contains(player.getBullets().get(rnd))){
                    main.addCardInHand(player.getBullets().get(rnd));
                    cardsOnField.add(player.getBullets().get(rnd));
                }else{i--;}
            }else{
                rnd = (int)(Math.random()*player.getEfectcards().size());
                if(!cardsOnField.contains(player.getEfectcards().get(rnd))){
                    main.addCardInHand(player.getEfectcards().get(rnd));
                    cardsOnField.add(player.getBullets().get(rnd));
                }else{i--;}
            }
        }
    }

    private void fillCards(){
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

    public ArrayList<EfectCard> getRndEffectCards(int amount){
        ArrayList<EfectCard> efectCard = player.getEfectcards();
        ArrayList<EfectCard> erg = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int number = (int)(Math.random()*efectCard.size());
            while(player.handCards.contains(efectCard.get(number))){
                number = (int)(Math.random()*efectCard.size());
            }
            player.handCards.add(efectCard.get(number));
            erg.add(efectCard.get(number));
        }
        return erg;
    }



    public void useEffectCard(EfectCard card){
        if(card.getCost()>=player.getEnergy()){
            System.out.println("Effektkarte "+card.getCardNameAsString()+" gespielt");
            cardsOnField.remove(card);
            main.removeCard(card);
            player.setEnergy(player.getEnergy()- card.getCost());
            main.setEnergy(player.getEnergy());
        }
    }

    public void shoot(Enemy enemy,boolean body){
        if(player.energy > 0 && player.bulletsInChamber.size()>0){
            int head = 1;
            if(!body){
                head*=2;
            }
             enemy.setHealth(enemy.getHealth()-
                     player.bulletsInChamber.get(0).getDamage()*head);
            main.setLifeOfEnemy(enemy);
            if(enemy.getHealth()<=0){
                main.removeEnemy(enemy);
                enemiesThisTurn.remove(enemy);
            }
            player.energy--;
            main.rotate();
            cardsOnField.remove(player.bulletsInChamber.get(0));
            player.bulletsInChamber.remove(0);
        }
    }

    public ArrayList<Bullet> getRndBullets(int amount){
        ArrayList<Bullet> bullets = player.getBullets();
        ArrayList<Bullet> erg = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int number = (int)(Math.random()*bullets.size());
            while(player.bulletsInChamber.contains(bullets.get(number)) ||
                    player.handCards.contains(bullets.get(number))){
                number = (int)(Math.random()*bullets.size());
            }
            player.handCards.add(bullets.get(number));
            erg.add(bullets.get(number));
        }
        return erg;
    }

    private void fillEfects(){
        for (Efect.EfectName name:
             Efect.EfectName.values()) {
            efectsInExistence.add(new Efect(name));
        }
    }

    public int chambersTaken(){
        return player.bulletsInChamber.size();
    }

    public void setBulletInSlot(Bullet bullet){
        player.bulletsInChamber.add(bullet);
    }

    public ArrayList<Bullet> getPlayersBullet(){
        return player.getBulletsInChamber();
    }

    public ArrayList<Enemy> getEnemiesThisTurn() {
        return enemiesThisTurn;
    }
}
