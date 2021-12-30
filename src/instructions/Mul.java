package instructions;

public class Mul extends Instruction{
    public void writeBack() {
        listener.onALU(getLabel(),vi * vj);
    }
}
