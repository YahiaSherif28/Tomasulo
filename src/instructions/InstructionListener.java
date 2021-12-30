package instructions;

public interface InstructionListener {

    void onLoad(int memoryAddress, String label);

    void onStore(int address, Double value);

    void onALU(String label, Double value);
}
