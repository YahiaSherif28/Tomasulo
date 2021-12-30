package instructions;

import static instructions.Status.FINISHED;

public class Store extends Instruction {
    int address;

    public void writeBack() {
        listener.onStore(getAddress(), vi + vj);
        status = FINISHED;
    }

    public int getAddress() {
        return address;
    }

    public void issue() {
        listener.issueStore(this);
    }
}
