package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Store extends Instruction {
    int address;

    public Store(int sourceRegister1, int memAddress, int latency, InstructionListener listener, String assemblyInstruction) {
        super(0, sourceRegister1, 0, latency, listener, assemblyInstruction);
        address = memAddress;
    }
    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onStore(getAddress(), vi);
        setStatus(FINISHED);
    }

    public void issue() {
        setStatus(ISSUED);
        listener.issueStore(this);
    }

    public String toString() {
        return "S.D " + super.toString() + String.format(", address: %s", address);
    }
    public String getInstruction() {
        return "S.D " + "F"+super.getSourceRegister1() + " "+ address;
    }
    public String getType(){
        return "S.D";
    }

    @Override
    public Load clone(){
        Load ret = new Load(this.sourceRegister1,this.address,this.cyclesLeft,this.listener,this.assemblyInstruction);
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
