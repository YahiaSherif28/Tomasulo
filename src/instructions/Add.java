package instructions;

public class Add extends Instruction{

    public void writeBack() {
        listener.onALU(getLabel(),vi + vj);
    }
    public void issue() {
        listener.issueALU(this);
    }
}
