package instructions;

import static instructions.Status.FINISHED;

public class Load extends Instruction {
    int address;

    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onLoad(getAddress(), getLabel());
        status = FINISHED;
    }
    public void issue() {
        listener.issueLoad(this);
    }
}
