import instructions.*;
import storage.Buffer;
import storage.Memory;
import storage.RegisterFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Tomasulo implements InstructionListener {
    Queue<Instruction> instructionQueue;
    ArrayList<Instruction> readyToWriteBack;
    Buffer addBuffer, mulBuffer, loadBuffer, storeBuffer;
    HashMap<String, ArrayList<Instruction>> waitingOnValue;
    RegisterFile registerFile;
    Memory memory;

    void go() {

    }

    public Buffer getBuffer(Instruction instruction) {
        if (instruction instanceof Add || instruction instanceof Sub) {
            return addBuffer;
        }
        if (instruction instanceof Mul || instruction instanceof Div) {
            return mulBuffer;
        }
        if (instruction instanceof Load) {
            return loadBuffer;
        }
        if (instruction instanceof Store) {
            return storeBuffer;
        }
        throw new RuntimeException();
    }


    public void issue() {
        if (instructionQueue.isEmpty()) {
            return;
        }
        Instruction current = instructionQueue.peek();
        Buffer buffer = getBuffer(current);
        String label = buffer.addInstruction(current);
        if (label != null) {
            instructionQueue.poll();
            current.issue();
        }
    }

    Instruction createInstruction(String line) {

    }



    public void issueALU(Instruction instruction) {
        registerFile.setLabel(instruction.getLabel(), instruction.getDestinationRegister());
        Double value1 = registerFile.getValue(instruction.getSourceRegister1());
        String label1 = registerFile.getLabel(instruction.getSourceRegister1());
        if (value1 != null) {
            instruction.setVi(value1);
        }
        if (label1 != null) {
            instruction.setQi(label1);
        }
        Double value2 = registerFile.getValue(instruction.getSourceRegister2());
        String label2 = registerFile.getLabel(instruction.getSourceRegister2());
        if (value2 != null) {
            instruction.setVj(value2);
        }
        if (label2 != null) {
            instruction.setQj(label2);
        }
    }
    public void issueStore(Store instruction) {
        Double value1 = registerFile.getValue(instruction.getSourceRegister1());
        String label1 = registerFile.getLabel(instruction.getSourceRegister1());
        if (value1 != null) {
            instruction.setVi(value1);
        }
        if (label1 != null) {
            instruction.setQi(label1);
        }
    }
    public void issueLoad(Store instruction) {
        registerFile.setLabel(instruction.getLabel(), instruction.getDestinationRegister());
    }

    public void onLoad(int memoryAddress, String label) {
        Double value = memory.get(memoryAddress);
        publishLabel(label, value);
    }

    public void onStore(int address, Double value) {
        memory.put(address, value);
    }

    public void onALU(String label, Double value) {
        publishLabel(label, value);
    }

    void publishLabel(String label, Double value) {
        if (waitingOnValue.containsKey(label)) {
            for (Instruction instruction : waitingOnValue.get(label)) {
                instruction.labelReady(label, value);
            }
            waitingOnValue.remove(label);
        }
        registerFile.publishLabel(label, value);
    }


}
