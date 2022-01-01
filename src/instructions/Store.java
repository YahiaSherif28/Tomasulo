package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Store extends Instruction {
    int address;

    public Store(int sourceRegister1, int memAddress, int latency, InstructionListener listener, String assemblyInstruction) {
        super(0, sourceRegister1, 0, latency, listener, assemblyInstruction);
        address = memAddress;
    }
    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onStore(getAddress(), vi);
        setStatus(FINISHED);
    }

    public void issue() {
        setStatus(ISSUED);
        listener.issueStore(this);
    }

    public String toString() {
        return "S.D " + super.toString() + String.format(", address: %s", address);
    }
}
