package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Mul extends Instruction {

    public Mul(int destinationRegister, int sourceRegister1 , int sourceRegister2, int latency, InstructionListener listener) {
        super(destinationRegister, sourceRegister1, sourceRegister2, latency, listener);
    }

    public void writeBack() {
        listener.onALU(getLabel(),vi * vj);
        status = FINISHED;
    }
    public void issue() {
        status = ISSUED;
        listener.issueALU(this);
    }

    public String toString() {
        return "MUL.D " + super.toString();
    }
}
