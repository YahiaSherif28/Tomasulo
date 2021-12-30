package instructions;

public class Load extends Instruction {
    int address;

    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onLoad(getAddress(), getLabel());
    }
    public void issue() {
        listener.issueLoad(this);
    }
}
