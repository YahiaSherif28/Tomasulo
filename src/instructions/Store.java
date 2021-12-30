package instructions;

public class Store extends Instruction{
    int address;
    public void writeBack() {
        listener.onStore(getAddress(),vi + vj);
    }

    private int getAddress() {
        return address;
    }
}
