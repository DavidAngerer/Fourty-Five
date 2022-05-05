import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Controller {
    float dificulty = 1;
    int stage = 1;

    Player player;

    ArrayList<Card> CardsinExistence;

    ArrayList<Efect> efectsInExistence;

    public Controller(float dificulty, int stage) {
        CardsinExistence=new ArrayList<>();
        efectsInExistence=new ArrayList<>();
        this.dificulty = dificulty;
        this.stage = stage;
        player = new Player(new ArrayList<>(),new ArrayList<>(),10);
        fillCards();
        fillEfects();
        for (int i = 0; i < 4; i++) {
            player.addCard(CardsinExistence.get(0));
        }
        player.addCard(CardsinExistence.get(1));
        nextStage();
    }

    public void nextStage(){
        stage++;
        int hpPool = (int)(Math.random()*stage)*4+(stage*4)+10;
        int damage = (int)(Math.random()*stage)*2+(stage*2);
        ArrayList<Enemy> enemiesThisTurn = new ArrayList<>();
        int enemyNumbers = (int)(Math.random()*3)+1;
        System.out.println(hpPool);
        System.out.println(enemyNumbers);
        for (int i = 0; i < enemyNumbers; i++) {
            Efect effekt = efectsInExistence.get((int)(efectsInExistence.size()*Math.random()));
            enemiesThisTurn.add(new Enemy(hpPool/enemyNumbers,damage,effekt));
        }
        main.newStage(stage,player.getBullets(),new ArrayList<>(), player.health, enemiesThisTurn);
        nextTurn(enemiesThisTurn);
    }

    private void nextTurn(ArrayList<Enemy> enemiesThisTurn){
//        while(player.health > 0){
//
//        }
    }

    private void fillCards(){
        Path path = Path.of("./res/CardData/Bullet.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                CardsinExistence.add(new Bullet(attributes));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillEfects(){
        for (Efect.EfectName name:
             Efect.EfectName.values()) {
            efectsInExistence.add(new Efect(name));
        }
    }
}
