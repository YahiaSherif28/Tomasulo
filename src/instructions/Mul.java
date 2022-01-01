package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Mul extends Instruction {

    public Mul(int destinationRegister, int sourceRegister1 , int sourceRegister2, int latency, InstructionListener listener, String assemblyInstruction) {
        super(destinationRegister, sourceRegister1, sourceRegister2, latency, listener, assemblyInstruction);
    }

    public void writeBack() {
        listener.onALU(getLabel(),vi * vj);
        setStatus(FINISHED);
    }

    public void issue() {
        setStatus(ISSUED);
        listener.issueALU(this);
    }

    public String toString() {
        return "MUL.D " + super.toString();
    }

    public String getInstruction(){
        return "MUL.D "+ "F"+super.getDestinationRegister()+" "+"F"+getSourceRegister1()+" "+"F"+getSourceRegister2();
    }
    public String getType(){
        return "MUL.D";
    }

    @Override
    public int getAddress() {
        return 0;
    }

    @Override
    public Mul clone(){
        Mul ret = new Mul(this.destinationRegister,this.sourceRegister1,this.sourceRegister2,this.cyclesLeft,this.listener,this.assemblyInstruction);
        ret.label= this.label;
        ret.qi = this.qi;
        ret.qj = this.qj;
        ret.vi = this.vi;
        ret.vj = this.vj;
        ret.issueCycle = this.issueCycle;
        ret.setStatus(this.getStatus());
        ret.finishExecCycle = this.finishExecCycle;
        ret.startExecCycle = this.startExecCycle;
        ret.writeBackCycle = this.writeBackCycle;
        return ret;
    }
}
