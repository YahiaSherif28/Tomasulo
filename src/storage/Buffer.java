package storage;

import instructions.Instruction;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static instructions.Status.*;

public class Buffer {
    String label;
    int size;
    Instruction[] buffer;

    public Buffer(String label, int size) {
        this.label = label;
        this.size = size;
        buffer = new Instruction[size];
    }

    public String addInstruction(Instruction instruction) { // return Label or null if couldn't add
        for (int i = 0; i < size; i++) {
            if (buffer[i] == null) {
                buffer[i] = instruction;
                instruction.setLabel(label + i);
                return instruction.getLabel();
            }
        }
        return null;
    }

    public void exec() {
        for (Instruction instruction : buffer) {
            if (instruction == null)
                continue;
            if (instruction.getStatus() == EXECUTING) {
                instruction.exec();
            }
        }
    }

    public ArrayList<Instruction> getReadyToWriteBack() {
        ArrayList<Instruction> list = new ArrayList<>();
        for (Instruction instruction : buffer) {
            if (instruction != null && instruction.getStatus() == READY_TO_WRITE_BACK) {
                list.add(instruction);
                instruction.setStatus(WRITING_BACK);
            }
        }
        return list;
    }
    public void changeIssuedToReady() {
        for (Instruction instruction : buffer) {
            if (instruction != null && instruction.getStatus() == ISSUED) {
                instruction.changeIssuedToReady();
            }
        }
    }

    public void removeFinishedInstructions() {
        for (int i = 0; i < size; i++) {
            if (buffer[i] != null && buffer[i].getStatus() == FINISHED) {
                buffer[i] = null;
            }
        }
    }

    public String toString() {
        String printValue = String.format("Buffer: %s, with size: %s\n", label, size);
        for(int i = 0; i < size; i++)
            if(buffer[i] != null)
                printValue += buffer[i].toString() + "\n";
        return printValue;
    }
}
