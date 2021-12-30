package instructions;

public class Sub extends Instruction{
    public void writeBack() {
        listener.onALU(getLabel(),vi - vj);
    }
}
