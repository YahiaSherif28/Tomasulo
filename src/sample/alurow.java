package sample;

public class alurow {
    String lab , op, vj,vk,qj,qk,busy;

    public String getLab() {
        return lab;
    }

    public String getOp() {
        return op;
    }

    public String getVj() {
        return vj;
    }

    public String getVk() {
        return vk;
    }

    public String getQj() {
        return qj;
    }

    public String getQk() {
        return qk;
    }

    public String getBusy() {
        return busy;
    }

    public alurow(String lab, String op, String vj, String vk, String qj, String qk, String busy) {
        this.lab = lab;
        this.op = op;
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
        this.busy = busy;
    }
}
