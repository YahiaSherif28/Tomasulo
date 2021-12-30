package instructions;

public abstract class Instruction {
    Double vi, vj;
    String qi, qj;
    int cyclesLeft;
    Status status;
    String label;

    public String getLabel() {
        return label;
    }

    InstructionListener listener;
    boolean canStartExec() { // return true if it can start

    }

    boolean exec() { // return has finished or not

    }
    boolean hasFinishedExecution() {

    }
    public void labelReady(String label, Double value) {

    }
    public abstract void writeBack();

}
