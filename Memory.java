import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memory {

    private Map<String, Integer> staticMemory;
    private Map<String, Integer> stackMemory;
    private List<Integer> heapMemory;
    
    private static Memory memory;

    private Memory() {
        staticMemory = new HashMap<>();
        stackMemory = new HashMap<>();
        heapMemory = new ArrayList<>();
    }

    public static Memory instance() {
        if (memory == null) {
            memory = new Memory();
        }
        return memory;
    }

    public int searchStaticMemory(String variable) {
        if (staticMemory.containsKey(variable)) return heapMemory.get(staticMemory.get(variable));
        
        System.out.println("Doesnt exist in static memory");
        return -1;
    }

    public int searchStackMemory(String variable) {
        if (stackMemory.containsKey(variable)) return heapMemory.get(stackMemory.get(variable));
        System.out.println("Doesnt exist in stack memory");
        return -1;
    }

    public void addToMemory(String variable, Object value, MemoryType memType) {
        int numVal = -1;
        if (value != null) numVal = (int) value;
        switch (memType) {
            case STACK:
                if (stackMemory.containsKey(variable)) {
                    int heapIndex = stackMemory.get(variable);
                    heapMemory.set(heapIndex, numVal);
                } else {
                    stackMemory.put(variable, heapMemory.size());
                    heapMemory.add(numVal);
                }
                break;
            case STATIC:
                if (staticMemory.containsKey(variable)) {
                    int heapIndex = staticMemory.get(variable);
                    heapMemory.set(heapIndex, numVal);
                } else {
                    staticMemory.put(variable, heapMemory.size());
                    heapMemory.add(numVal);
                }
                break;
            default:
                throw new IllegalArgumentException("NONE/HEAP shouldn't be here");
        }
    }

    public void declareNew(String variable) {
        // called in case of id = new type assignment
        Map<String, Integer> destinationMap;
        // check if global has this variable
        if (staticMemory.containsKey(variable)) {
            destinationMap = staticMemory;
        } else {
            // if global doesnt have it, store to local/stack
            destinationMap = stackMemory;
        }
        if (destinationMap.containsKey(variable)) {
            destinationMap.replace(variable, heapMemory.size());
        } else {
            destinationMap.put(variable, heapMemory.size());
        }
        // add to end of heap
        heapMemory.add(-1);
    }

    public void reassign(String leftSide, String rightSide) {
        // called in case of id = ref id type assignment
        Map<String, Integer> leftMap, rightMap;
        // check if local has left variable
        if (stackMemory.containsKey(leftSide)) {
            leftMap = stackMemory;
        } else {
            // if local doesnt have it, store to global
            leftMap = staticMemory;
        }
        // check if local has right variable
        if (stackMemory.containsKey(rightSide)) {
            rightMap = stackMemory;
        } else {
            rightMap = staticMemory;
        }

        // index of right side variable
        int idx = rightMap.get(rightSide);
        leftMap.replace(leftSide, idx); // now left points to wherever right is in the heap
    }
}
