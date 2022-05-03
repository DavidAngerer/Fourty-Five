public class Efect {
    int cyclesLeft;

    public String efectDiscription;
    private enum EfectName{
        NormalBullet,PoisonBullet
    }
    public EfectName efectName;

    public void addCycles(int cycles){
        cyclesLeft += cycles;
    }
}
