package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Sub extends Instruction {

    public Sub(int destinationRegister, int sourceRegister1 , int sourceRegister2, int latency, InstructionListener listener, String assemblyInstruction) {
        super(destinationRegister, sourceRegister1, sourceRegister2, latency, listener, assemblyInstruction);
    }

    public void writeBack() {
        listener.onALU(getLabel(), vi - vj);
        setStatus(FINISHED);
    }

    public void issue() {
        setStatus(ISSUED);
        listener.issueALU(this);
    }

    public String toString() {
        return "SUB.D " + super.toString();
    }
}
