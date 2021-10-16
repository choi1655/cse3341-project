import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Memory {

    // global variables
    private Map<String, Integer> staticMemory;
    // local variables
    private Map<String, Integer> stackMemory;
    // heap memory
    private List<Integer> heapMemory;

    // contains variables that are refs
    private Set<String> refs;
    
    private static Memory memory;

    private Memory() {
        staticMemory = new HashMap<>();
        stackMemory = new HashMap<>();
        heapMemory = new ArrayList<>();
        refs = new HashSet<>();
    }

    public static Memory instance() {
        if (memory == null) {
            memory = new Memory();
        }
        return memory;
    }

    public boolean containsVariable(String variable, MemoryType memType) {
        if (memType == MemoryType.STATIC) {
            return staticMemory.containsKey(variable);
        } else {
            return stackMemory.containsKey(variable);
        }
    }

    // a = 10;
    //          a = variable
    //          value = 10
    //          memory = a:10
    // b = new;
    // c = new;
    // b = ref c;
    // b = a;
    //          memory = a:10, b:1, c:1
    //          heap = null, 10

    public void declareNewInt(String variable, int value, MemoryType memType) {
        // a = 10;

        // what if variable exists? replace
        Map<String, Integer> destination = staticMemory;
        if (memType == MemoryType.STACK) {
            destination = stackMemory;
        }
        destination.put(variable, value);
    }

    public void declareNewRef(String variable, MemoryType memType) {
        // called in case of id = new type assignment
        // assuming variable doesnt exist in memory
        Map<String, Integer> destinationMap;
        if (memType == MemoryType.STACK) {
            destinationMap = stackMemory;
        } else {
            destinationMap = staticMemory;
        }
        if (destinationMap.containsKey(variable)) {
            destinationMap.replace(variable, heapMemory.size());
        } else {
            destinationMap.put(variable, heapMemory.size());
            // add this variable to the ref set
            refs.add(variable);
        }
        heapMemory.add(-1);
    }

    public void reassignRef(String leftSide, String rightSide) {
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

    public void reassignInt(String leftSide, int value) {
        // handles cases like a = 10; a = 11;
        // assuming variable exists
        Map<String, Integer> leftMap;
        // first, check if local has left variable
        if (stackMemory.containsKey(leftSide)) {
            leftMap = stackMemory;
        } else {
            // if local doesnt have it, store to global
            leftMap = staticMemory;
        }
        leftMap.replace(leftSide, value);
    }

    public void reassignRefToInt(String leftSide, int value) {
        // called in case of x = 10; a = new; a = x;
        // called in case of id = ref id type assignment

        // make sure leftside is a ref variable
        if (!refs.contains(leftSide)) {
            System.out.println("ERROR refs set doesnt contain " + leftSide);
            System.exit(-1);
        }
        Map<String, Integer> leftMap;
        // check if local has left variable
        if (stackMemory.containsKey(leftSide)) {
            leftMap = stackMemory;
        } else {
            // if local doesnt have it, store to global
            leftMap = staticMemory;
        }
        // index of right side variable
        int idx = leftMap.get(leftSide);
        heapMemory.set(idx, value);
    }

    public void reassignIntToRef(String leftSide, String rightSide) {
        // called in case of x = 10; b = new; x = b;
        Map<String, Integer> leftMap, rightMap;
        // check if local has left variable
        if (stackMemory.containsKey(leftSide)) {
            leftMap = stackMemory;
        } else {
            // if local doesnt have it, store to global
            leftMap = staticMemory;
        }
        // check if local has left variable
        if (stackMemory.containsKey(rightSide)) {
            rightMap = stackMemory;
        } else {
            // if local doesnt have it, store to global
            rightMap = staticMemory;
        }
        // index of right side variable
        int idx = rightMap.get(rightSide);
        leftMap.replace(leftSide, idx);

        // leftside is now a ref so add to ref set
        refs.add(leftSide);
    }

    public int getVariableValue(String variable) {
        Map<String, Integer> map = stackMemory;
        // if variable doesnt exist in local, must be in global
        if (!map.containsKey(variable)) {
            map = staticMemory;
        }

        boolean isRef = refs.contains(variable);
        int val = map.get(variable);
        if (isRef) {
            val = heapMemory.get(val);
        }
        return val;
    }
}
