package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Div extends Instruction {

    public Div(int destinationRegister, int sourceRegister1 , int sourceRegister2, int latency, InstructionListener listener) {
        super(destinationRegister, sourceRegister1, sourceRegister2, latency, listener);
    }

    public void writeBack() {
        listener.onALU(getLabel(), vi / vj);
        status = FINISHED;
    }

    public void issue() {
        status = ISSUED;
        listener.issueALU(this);
    }

    public String toString() {
        return "DIV.D " + super.toString();
    }
}
