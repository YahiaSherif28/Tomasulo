package instructions;

public abstract class Instruction {


    int destinationRegister, sourceRegister1, sourceRegister2;
    Double vi, vj;



    String qi, qj;
    int cyclesLeft;
    Status status;
    String label;
    InstructionListener listener;

    public void setVi(Double vi) {
        this.vi = vi;
    }

    public void setVj(Double vj) {
        this.vj = vj;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }
    public int getDestinationRegister() {
        return destinationRegister;
    }

    public int getSourceRegister1() {
        return sourceRegister1;
    }

    public int getSourceRegister2() {
        return sourceRegister2;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    boolean canStartExec() { // return true if it can start

    }

    boolean exec() { // return has finished or not

    }

    boolean hasFinishedExecution() {

    }

    public void labelReady(String label, Double value) {

    }

    public abstract void writeBack();

    public abstract void issue();

}
