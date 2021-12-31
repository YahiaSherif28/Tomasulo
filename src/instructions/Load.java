package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Load extends Instruction {
    int address;

    public Load(int destinationRegister, int memAddress, int latency, InstructionListener listener) {
        super(destinationRegister, 0, 0, latency, listener);
        address = memAddress;
    }

    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onLoad(getAddress(), getLabel());
        status = FINISHED;
    }
    public void issue() {
        status = ISSUED;
        listener.issueLoad(this);
    }
}
