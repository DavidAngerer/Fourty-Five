public class Efect {
    int cyclesLeft;

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
    }
}
