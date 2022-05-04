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
        this.dificulty = dificulty;
        this.stage = stage;
        player = new Player(new ArrayList<>(),new ArrayList<>(),10);
        fillCards();
        player.addCard(new Bullet(Card.CardName.Bullet));
    }

    private void fillCards(){
        Path path = Path.of("CardData/Bullet.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                CardsinExistence.add(new Bullet(attributes));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
