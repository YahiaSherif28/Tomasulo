package storage;

import java.util.Arrays;

public class Memory {
    final int MAX_POSSIBLE_SIZE = (int)1e6;
    int size;
    Double[] values;
    public Memory(int Memorysize){
        size = Math.min(Memorysize, MAX_POSSIBLE_SIZE);
        values = new Double[size];
        Arrays.fill(values, 1.0);
    }

    public Double get(int address){
        if(address>=0 && address<size)
            return values[address];
        return null;
    }

    public boolean put(int address, Double value){
        if(address>=0 && address<size) {
            values[address] = value;
            return true;
        }
        return false;
    }
    public String getFilledCells(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if(values[i] != null)
                sb.append(i+" -> "+(values[i]) + "\n");
        }
        return sb.toString();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(i+" -> "+(values[i]==null?"Empty":values[i]) + "\n");
        }
        return sb.toString();
    }
}
