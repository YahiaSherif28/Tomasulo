package sample;

public class memrow {
    String lab,address,busy,v,q;

    public memrow(String lab, String address, String busy, String v, String q) {
        this.lab = lab;
        this.address = address;
        this.busy = busy;
        this.v = v;
        this.q = q;
    }

    public memrow(String lab, String address, String busy) {
        this.lab = lab;
        this.address = address;
        this.busy = busy;
    }

    public String getV() {
        return v;
    }

    public String getQ() {
        return q;
    }

    public String getLab() {
        return lab;
    }

    public String getAddress() {
        return address;
    }

    public String getBusy() {
        return busy;
    }
}
