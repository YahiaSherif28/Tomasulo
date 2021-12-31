import instructions.Instruction;
import instructions.InstructionListener;

import java.util.Queue;

public class InputReader {
    static int addLatency, subLatency, mulLatency, divLatency, loadLatency, storeLatency;
    // TODO : implement this, read from file, see TODO
    private void initInstructionQueue(String filePath) {

    }
    // TODO: set values of addLatency, subLatency, mulLatency, divLatency, loadLatency, storeLatency
    // from input file
    private void readLatency(String filePath) {

    }
    // TODO: implement this: return Add, Sub, Div, Mul, Load, Store instance depending on the string
    // Set values of destination register, sourceRegister1,sourceRegister2 in ALU operations
    // set value of destination register and address in Load
    // set value of sourceRegister1 and address in Store
    // set value of cyclesLeft in all instructions depending on value in the constructor
    // set value of listener as in the constructor
    
    private static Instruction createInstruction(String s, int cycles, InstructionListener listener) {
        
    }

    public static Queue<Instruction> readInput(String filePath) {
        
    }
}
