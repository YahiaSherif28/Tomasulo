package storage;

import instructions.Instruction;

import static instructions.Status.FINISHED;

public class Buffer {
    String label;
    int size;
    Instruction[] buffer;

    public Buffer(String label, int size){
        this.label = label;
        this.size = size;
        buffer = new Instruction[size];
    }

    public String addInstruction(Instruction instruction) { // return Label or null if couldn't add
        for (int i = 0; i < size; i++) {
            if(buffer[i] == null){
                buffer[i] = instruction;
                instruction.setLabel(label+i);
                return instruction.getLabel();
            }
        }
        return null;
    }
    void exec() {

    }
    void removeFinishedInstructions() {
        for (int i = 0; i < size; i++) {
            if(buffer[i] != null && buffer[i].getStatus() == FINISHED){
                buffer[i] = null;
            }
        }
    }
}
