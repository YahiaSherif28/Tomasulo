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

    public void writeLabelInRegisterFile(int address, String label) {

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
            writeLabelInRegisterFile(current.getDestinationRegister(), label);
        }
    }

    Instruction createInstruction(String line) {

    }

    void writeInMemory(int address, Double value) {

    }

    Double readFromMemory(int address) {

    }

    public void onLoad(int memoryAddress, String label) {
        Double value = readFromMemory(memoryAddress);
        publishLabel(label, value);
    }

    public void onStore(int address, Double value) {
        writeInMemory(address, value);
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
