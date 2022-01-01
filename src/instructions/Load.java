package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Load extends Instruction {
    int address;

    public Load(int destinationRegister, int memAddress, int latency, InstructionListener listener, String assemblyInstruction) {
        super(destinationRegister, 0, 0, latency, listener, assemblyInstruction);
        address = memAddress;
    }

    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onLoad(getAddress(), getLabel());
        setStatus(FINISHED);
    }

    public void issue() {
        setStatus(ISSUED);
        listener.issueLoad(this);
    }

    public String toString() {
        return "L.D " + super.toString() + String.format(", address: %s", address);
    }
}
