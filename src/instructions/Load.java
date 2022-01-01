package instructions;

import static instructions.Status.FINISHED;
import static instructions.Status.ISSUED;

public class Load extends Instruction {
    int address;

    public Load(int destinationRegister, int memAddress, int latency, InstructionListener listener, String assemblyInstruction) {
        super(destinationRegister, 0, 0, latency, listener, assemblyInstruction);
        address = memAddress;
    }

    public int getAddress() {
        return address;
    }

    public void writeBack() {
        listener.onLoad(getAddress(), getLabel());
        setStatus(FINISHED);
    }

    public void issue() {
        setStatus(ISSUED);
        listener.issueLoad(this);
    }

    public String toString() {
        return "L.D " + super.toString() + String.format(", address: %s", address);
    }
    public String getInstruction() {
        return "L.D " +"F"+super.getDestinationRegister() + " "+address;
    }
    public String getType(){
        return "L.D ";
    }

    @Override
    public Load clone(){
        Load ret = new Load(this.destinationRegister,this.address,this.cyclesLeft,this.listener,this.assemblyInstruction);
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
