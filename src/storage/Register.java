package storage;

public class Register {
    int num;    // e.g. 1 as in F1
    Double value;
    // Label of instruction that will set this register's value
    // e.g M1
    String waitingOnLabel;

    Register(int idx){
        this.num = idx;
        value = 0.0;
        waitingOnLabel = null;
    }

    public String toString() {
        return String.format("Register: %s => Value: %s, waitingOnLabel: %s", num, value, waitingOnLabel);
    }
}
