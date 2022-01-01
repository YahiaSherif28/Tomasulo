package instructions;

import static instructions.Status.*;

public abstract class Instruction {

    int destinationRegister, sourceRegister1, sourceRegister2;
    Double vi, vj;
    String qi, qj;
    int cyclesLeft;
    private Status status;
    String label;
    InstructionListener listener;
    String assemblyInstruction;
    Integer issueCycle, startExecCycle, finishExecCycle, writeBackCycle;

    public Instruction(int destinationRegister, int sourceRegister1, int sourceRegister2, int cyclesLeft,
                       InstructionListener listener, String assemblyInstruction) {
        this.destinationRegister = destinationRegister;
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
        this.cyclesLeft = cyclesLeft;
        this.listener = listener;
        this.assemblyInstruction = assemblyInstruction;
        setStatus(IN_QUEUE);
    }

    public Integer getIssueCycle() {
        return issueCycle;
    }

    public Integer getStartExecCycle() {
        return startExecCycle;
    }

    public Integer getFinishExecCycle() {
        return finishExecCycle;
    }

    public Integer getWriteBackCycle() {
        return writeBackCycle;
    }

    public abstract Instruction clone();
    public abstract String getInstruction();
    public abstract String getType();
    public abstract int getAddress();

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

    public Double getVi() {
        return vi;
    }

    public Double getVj() {
        return vj;
    }

    public String getQi() {
        return qi;
    }

    public String getQj() {
        return qj;
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
        Integer cycle = listener.getCurrentCycle();
        if(status == ISSUED)
            issueCycle = cycle;
        else if(status == EXECUTING)
            startExecCycle = cycle;
        else if(status == READY_TO_WRITE_BACK)
            finishExecCycle = cycle;
        else if(status == FINISHED)
            writeBackCycle = cycle;
    }

    public void exec() { // return has finished or not
        cyclesLeft--;
        if (cyclesLeft == 0) {
            setStatus(READY_TO_WRITE_BACK);
        }
    }


    public void labelReady(String label, Double value) {
        if (qi != null && qi.equals(label)) {
            qi = null;
            vi = value;
        }
        if (qj != null && qj.equals(label)) {
            qj = null;
            vj = value;
        }
        if (status == WAITING_ON_VALUE && qi == null && qj == null) {
            setStatus(EXECUTING);
        }
    }

    public abstract void writeBack();

    public abstract void issue();

    public void changeIssuedToReady() {
        setStatus(WAITING_ON_VALUE);
        if (qi == null && qj == null) {
            setStatus(EXECUTING);
        }
    }

    public String toString() {
        return String.format("destinationRegister: %s, sourceRegister1: %s, sourceRegister2: %s, vi: %s," +
                        " vj: %s, qi: %s, qj: %s, cyclesLeft: %s, status: %s, label: %s",
                destinationRegister, sourceRegister1, sourceRegister2, vi, vj, qi, qj, cyclesLeft, status, label);
    }
}
