import instructions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class InputReader {
    static int addLatency, subLatency, mulLatency, divLatency, loadLatency, storeLatency;

    public static Queue<Instruction> readInput(String filePath, InstructionListener listener) {
        try {
            readLatency(filePath);
            ArrayList<String> assemblyInstructions = readInstructions(filePath);
            return generateInstructionQueue(assemblyInstructions, listener);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new LinkedList<>();
    }

    private static void readLatency(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        addLatency = Integer.parseInt(tokenizer.nextToken());
        subLatency = Integer.parseInt(tokenizer.nextToken());
        mulLatency = Integer.parseInt(tokenizer.nextToken());
        divLatency = Integer.parseInt(tokenizer.nextToken());
        loadLatency = Integer.parseInt(tokenizer.nextToken());
        storeLatency = Integer.parseInt(tokenizer.nextToken());
    }

    private static ArrayList<String> readInstructions(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        // Ignore first line that contains latencies
        reader.readLine();
        // Read the program instructions
        ArrayList<String> assemblyInstructions = new ArrayList<>();
        while(reader.ready()) {
            assemblyInstructions.add(reader.readLine());
        }
        return assemblyInstructions;
    }

    private static Queue<Instruction> generateInstructionQueue(ArrayList<String> assemblyInstructions, InstructionListener listener) {
        Queue<Instruction> instructionQueue = new LinkedList<>();
        for(String instruction : assemblyInstructions) {
            instructionQueue.add(createInstruction(instruction, listener));
        }
        return instructionQueue;
    }

    private static Instruction createInstruction(String assemblyInstruction, InstructionListener listener) {
        StringTokenizer tokenizer = new StringTokenizer(assemblyInstruction);
        String instructionType = tokenizer.nextToken();
        int destinationRegister = Integer.parseInt(tokenizer.nextToken().substring(1));
        String sourceRegister1OrAddress = tokenizer.nextToken();
        Instruction newInstruction = null;
        if(instructionType.equals("L.D")) {
            int memAddress = Integer.parseInt(sourceRegister1OrAddress);
            newInstruction = new Load(destinationRegister, memAddress, loadLatency, listener);
        } else if(instructionType.equals("S.D")) {
            int memAddress = Integer.parseInt(sourceRegister1OrAddress);
            newInstruction = new Store(destinationRegister, memAddress, storeLatency, listener);
        } else {
            int sourceReg1 = Integer.parseInt(sourceRegister1OrAddress.substring(1));
            int sourceReg2 = Integer.parseInt(tokenizer.nextToken().substring(1));
            if(instructionType.equals("ADD.D")) {
                newInstruction = new Add(destinationRegister, sourceReg1, sourceReg2, addLatency, listener);
            } else if(instructionType.equals("SUB.D")) {
                newInstruction = new Sub(destinationRegister, sourceReg1, sourceReg2, subLatency, listener);
            } else if(instructionType.equals("MUL.D")) {
                newInstruction = new Mul(destinationRegister, sourceReg1, sourceReg2, mulLatency, listener);
            } else if(instructionType.equals("DIV.D")) {
                newInstruction = new Div(destinationRegister, sourceReg1, sourceReg2, divLatency, listener);
            }
        }
        return newInstruction;
    }
}
