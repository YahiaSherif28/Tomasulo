package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Store extends Instruction {
    int address;

    public Store(int sourceRegister1, int memAddress, int latency, InstructionListener listener) {
        super(0, sourceRegister1, 0, latency, listener);
        address = memAddress;
    }

    public void writeBack() {
        listener.onStore(getAddress(), vi);
        status = FINISHED;
    }

    public int getAddress() {
        return address;
    }

    public void issue() {
        status = ISSUED;
        listener.issueStore(this);
    }

    public String toString() {
        return "S.D " + super.toString() + String.format(", address: %s", address);
    }
}
