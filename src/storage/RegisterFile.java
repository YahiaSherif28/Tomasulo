package storage;

public class RegisterFile {
    int size;
    Register[] registers;

    public RegisterFile(int size){
        this.size = size;
        registers = new Register[size];
        for (int i = 0; i < size; i++) {
            registers[i] = new Register(i);
        }
    }

    // Inform the registers with the values they are waiting on from Instruction with the given label
    public void publishLabel(String label, Double value){
        for(Register reg: registers){
            if(label.equals(reg.waitingOnLabel)){
                reg.waitingOnLabel = null;
                reg.value = value;
            }
        }
    }

    public void setLabel(String label, int address){
        registers[address].value = null;
        registers[address].waitingOnLabel = label;
    }

    public String getLabel(int address){
        return registers[address].waitingOnLabel;
    }

    public Double getValue(int address){
        return registers[address].value;
    }

    public String toString() {
        String printValue = "Register File with size: " + size + "\n";
        for(int i = 0; i < size; i++)
            printValue += registers[i].toString() + "\n";
        return printValue;
    }

}
