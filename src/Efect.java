public class Efect {
    int cyclesLeft;

    boolean killOnLastTurn = false;
    public String efectDiscription;
    public enum EfectName{
        THORNES,BURN,WEAK,UNLUCKY,POISOND,REMORSE,RAGE
    }
    public EfectName efectName;

    public void addCycles(int cycles){
        cyclesLeft += cycles;
    }

    public Efect(EfectName efectName) {
        this.efectName = efectName;
        cyclesLeft=1;
    }

    public Efect(EfectName efectName, int cycles, boolean killOnLastTurn){
        this.efectName = efectName;
        cyclesLeft=cycles;
        this.killOnLastTurn = killOnLastTurn;
    }
}
