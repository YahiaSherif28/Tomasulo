package Tomasulo;

import instructions.Instruction;
import storage.Buffer;
import storage.Memory;
import storage.RegisterFile;

import java.util.ArrayList;
public class State {
    ArrayList<Instruction>  allInstructions;
    Buffer addBuffer, mulBuffer, loadBuffer, storeBuffer;
    RegisterFile registerFile;
    Memory memory;
    public State(ArrayList<Instruction>  allInstructions,    Buffer addBuffer,Buffer mulBuffer,Buffer loadBuffer,Buffer storeBuffer,  RegisterFile registerFile,Memory memory){
        this.allInstructions = new ArrayList<>();
        for (Instruction i : allInstructions) this.allInstructions.add(i.clone());
        this.addBuffer = addBuffer.clone();
        this.mulBuffer = mulBuffer.clone();
        this.loadBuffer = loadBuffer.clone();
        this.storeBuffer = storeBuffer.clone();
        this.registerFile = registerFile.clone();
        this.memory = memory.clone();
    }

    public ArrayList<Instruction> getAllInstructions() {
        return allInstructions;
    }

    public Buffer getAddBuffer() {
        return addBuffer;
    }

    public Buffer getMulBuffer() {
        return mulBuffer;
    }

    public Buffer getLoadBuffer() {
        return loadBuffer;
    }

    public Buffer getStoreBuffer() {
        return storeBuffer;
    }

    public RegisterFile getRegisterFile() {
        return registerFile;
    }

    public Memory getMemory() {
        return memory;
    }
}
