package instructions;

import static instructions.Status.FINISHED;

public class Div extends Instruction {
    public void writeBack() {
        listener.onALU(getLabel(), vi / vj);
        status = FINISHED;
    }

    public void issue() {
        listener.issueALU(this);
    }
}
