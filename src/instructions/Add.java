package instructions;

public class Add extends Instruction{

    public void writeBack() {
        listener.onALU(getLabel(),vi + vj);
    }
}
