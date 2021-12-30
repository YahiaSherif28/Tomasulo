import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Tomasulo implements InstructionListener{
    Queue<Instruction> instructionQueue;
    ArrayList<Instruction> readyToWriteBack;
    Buffer addBuffer, mulBuffer, loadBuffer, storeBuffer;
    HashMap<String, ArrayList<Instruction>> waitingOnValue;
    RegisterFile registerFile;
    Memory memory;

    void go() {

    }
    Instruction createInstruction(String line) {

    }


}
