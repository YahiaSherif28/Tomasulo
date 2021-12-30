package instructions;

public class Sub extends Instruction{
    public void writeBack() {
        listener.onALU(getLabel(),vi - vj);
    }
    public void issue() {
        listener.issueALU(this);
    }
}
