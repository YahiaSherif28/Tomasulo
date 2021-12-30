package instructions;

public class Store extends Instruction{
    int address;
    public void writeBack() {
        listener.onStore(getAddress(),vi + vj);
    }

    public int getAddress() {
        return address;
    }
    public void issue() {
        listener.issueStore(this);
    }
}
