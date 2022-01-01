import instructions.*;
import storage.Buffer;
import storage.Memory;
import storage.RegisterFile;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Tomasulo implements InstructionListener {
    Queue<Instruction> instructionQueue;
    Queue<Instruction> readyToWriteBack;
    Buffer addBuffer, mulBuffer, loadBuffer, storeBuffer;
    Buffer[] buffers;
    HashMap<String, ArrayList<Instruction>> waitingOnValue;
    RegisterFile registerFile;
    Memory memory;

    public Tomasulo(String filePath) {
        instructionQueue = new LinkedList<>();
        readyToWriteBack = new LinkedList<>();
        addBuffer = new Buffer("A", 3);
        mulBuffer = new Buffer("M", 2);
        loadBuffer = new Buffer("L", 5);
        storeBuffer = new Buffer("S", 5);
        buffers = new Buffer[]{addBuffer, mulBuffer, loadBuffer, storeBuffer};
        waitingOnValue = new HashMap<>();
        registerFile = new RegisterFile(32);
        memory = new Memory(128);
        instructionQueue = InputReader.readInput(filePath, this);
    }


    void go() {
        int loopCounter = 0;
        showState(loopCounter);
        while (loopCounter < 20) {
            loopCounter++;
            issue();
            exec();
            writeBack();
            showState(loopCounter);
            updateStatus();
        }
    }

    private void showState(int loopCounter) {
        System.out.println("Current Loop: " + loopCounter);
        System.out.println("==============");
        System.out.println("Instruction Queue:");
        System.out.println("===================");
        for (Instruction i : instructionQueue)
            System.out.println(i);
        System.out.println("---------------------------------------------------");
        System.out.println("Ready To Write Back Instructions:");
        System.out.println("===================");
        for (Instruction i : readyToWriteBack)
            System.out.println(i);
        System.out.println("---------------------------------------------------");
        System.out.println("Add/Sub Buffer:");
        System.out.println("===================");
        System.out.println(addBuffer);
        System.out.println("---------------------------------------------------");
        System.out.println("Mul/Div Buffer:");
        System.out.println("===================");
        System.out.println(mulBuffer);
        System.out.println("---------------------------------------------------");
        System.out.println("Load Buffer:");
        System.out.println("===================");
        System.out.println(loadBuffer);
        System.out.println("---------------------------------------------------");
        System.out.println("Store Buffer:");
        System.out.println("===================");
        System.out.println(storeBuffer);
        System.out.println("---------------------------------------------------");
        System.out.println("Register File:");
        System.out.println("===================");
        System.out.println(registerFile);
        System.out.println("---------------------------------------------------");
        System.out.println("Memory:");
        System.out.println("===================");
        System.out.println(memory);
        System.out.println("---------------------------------------------------");
        //HashMap<String, ArrayList<Instruction>> waitingOnValue;
        System.out.println("****************************************************");
    }


    public void updateStatus() {
        for (Buffer buffer : buffers) {
            buffer.changeIssuedToReady();
            readyToWriteBack.addAll(buffer.getReadyToWriteBack());
            buffer.removeFinishedInstructions();
        }
    }

    public void writeBack() {
        if (!readyToWriteBack.isEmpty()) {
            Instruction instruction = readyToWriteBack.poll();
            instruction.writeBack();
        }
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

    public void exec() {
        for (Buffer buffer : buffers) {
            buffer.exec();
        }
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

    public void addInWaitingOnValue(String label, Instruction instruction) {
        ArrayList<Instruction> list = waitingOnValue.getOrDefault(label, new ArrayList<>());
        list.add(instruction);
        waitingOnValue.put(label, list);
    }

    public void issueALU(Instruction instruction) {

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
        if (instruction.getQi() != null) {
            addInWaitingOnValue(instruction.getQi(), instruction);
        }
        if (instruction.getQj() != null) {
            addInWaitingOnValue(instruction.getQj(), instruction);
        }
        registerFile.setLabel(instruction.getLabel(), instruction.getDestinationRegister());
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
        if (instruction.getQi() != null) {
            addInWaitingOnValue(instruction.getQi(), instruction);
        }
    }

    public void issueLoad(Load instruction) {
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

    public static void main(String[] args) {
        Tomasulo test = new Tomasulo("test1.txt");
        test.go();
    }


}
