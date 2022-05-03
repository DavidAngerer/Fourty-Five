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
        player.addCard(new Bullet(Card.CardName.NormalBullet));
    }

    private void fillCards(){

    }
}
