import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Memory {

    // global variables
    private Map<String, Integer> staticMemory;
    // local variables
    private Stack<Map<String, Integer>> stackMemory;
    private Map<String, Integer> currentStack;
    // heap memory
    private List<Integer> heapMemory;

    // contains variables that are refs
    private Stack<Set<String>> refStack;
    private Set<String> currentRefs;
    
    private static Memory memory;

    // minimum value of int to mark NULL values in the heap.
    private final int NULLVAL = Integer.MIN_VALUE;

    private Memory() {
        staticMemory = new HashMap<>();
        stackMemory = new Stack<>();
        currentStack = new HashMap<>();
        heapMemory = new ArrayList<>();
        refStack = new Stack<>();
        currentRefs = new HashSet<>();
    }

    public static Memory instance() {
        if (memory == null) {
            memory = new Memory();
        }
        return memory;
    }

    public boolean isRef(String var) {
        return currentRefs.contains(var) || searchRefInStack(var);
    }

    public boolean searchRefInStack(String var) {
        if (refStack.isEmpty()) return false;
        Set<String> set = refStack.pop();
        boolean found = false;
        if (set.contains(var)) {
            found = true;
        } else {
            found = searchRefInStack(var);
        }
        refStack.push(set);
        return found;
    }

    public void incrementScope() {
        stackMemory.push(currentStack);
        currentStack = new HashMap<>();
        refStack.push(currentRefs);
        currentRefs = new HashSet<>();
    }

    public void decrementScope() {
        Map<String, Integer> map = stackMemory.pop();
        currentStack = map;
        currentRefs = refStack.pop();
    }

    public boolean containsVariable(String variable, MemoryType memType) {
        boolean varFound = false;
        // check local stack first
        if (memType == MemoryType.STACK) {
            // check local, if DNE check stack
            if (currentStack.containsKey(variable)) {
                varFound = true;
            } else {
                varFound = checkEntireStack(variable);
            }
        }
        // if varFound is still false, check global
        if (!varFound) {
            return staticMemory.containsKey(variable);
        } else {
            return true;
        }
    }

    public boolean checkEntireStack(String variable) {
        if (stackMemory.isEmpty()) return false;

        Map<String, Integer> map = stackMemory.pop();
        boolean found = map.containsKey(variable);
        if (!found) {
            found = checkEntireStack(variable);
        }
        stackMemory.push(map);
        return found;
    }

    public int valueInStack(String variable) {
        if (stackMemory.isEmpty()) throw new IllegalArgumentException("Variable not found");

        Map<String, Integer> map = stackMemory.pop();
        int value;
        if (map.containsKey(variable)) {
            value = map.get(variable);
        } else {
            value = valueInStack(variable);
        }
        stackMemory.push(map);
        return value;
    }

    public void declareNewInt(String variable, int value, MemoryType memType) {
        // a = 10;

        Map<String, Integer> destination = staticMemory;
        if (memType == MemoryType.STACK) {
            destination = currentStack;
        }
        if (isRef(variable)) {
            int idx = destination.get(variable);
            heapMemory.set(idx, value);
        } else {
            destination.put(variable, value);
        }
    }

    public void declareNewRef(String variable, MemoryType memType) {
        // called in case of id = new type assignment

        if (currentStack.containsKey(variable)) {
            int idx = currentStack.get(variable);
            heapMemory.set(idx, 0);
            return;
        }
        // if variable exists in stack and has null, we dont need to go further
        if (checkEntireStack(variable)) {
            if (getVariableValue(variable) == NULLVAL) {
                // set it to 0
                int idx = valueInStack(variable);
                heapMemory.set(idx, 0);
                return;
            }
        }
        // if variable exists in global and has null, set it to 0 and we dont need to go further
        if (staticMemory.containsKey(variable) && heapMemory.get(staticMemory.get(variable)) == NULLVAL) {
            heapMemory.set(staticMemory.get(variable), 0);
            return;
        }

        // at this point, we know ref is a brand new variable

        Map<String, Integer> destinationMap;
        if (memType == MemoryType.STACK) {
            destinationMap = currentStack;
        } else {
            destinationMap = staticMemory;
        }
        destinationMap.put(variable, heapMemory.size());
        heapMemory.add(NULLVAL);
        currentRefs.add(variable);
    }

    public void reassignRef(String leftSide, String rightSide) {

        boolean leftInStack = checkEntireStack(leftSide);
        boolean rightInStack = checkEntireStack(rightSide);

        // called in case of id = ref id type assignment
        Map<String, Integer> leftMap, rightMap;
        int rightIdx = 0;
        // check if local has right variable
        if (currentStack.containsKey(rightSide)) {
            rightMap = currentStack;
        } else {
            rightMap = staticMemory;
        }
        if (rightInStack) {
            rightIdx = valueInStack(rightSide);
        } else {
            rightIdx = rightMap.get(rightSide);
        }


        // check if local has left variable
        if (currentStack.containsKey(leftSide)) {
            leftMap = currentStack;
        } else {
            // if local doesnt have it, must be in global
            leftMap = staticMemory;
        }

        if (leftInStack) {
            reassignRefInStack(leftSide, rightIdx);
        } else {
            leftMap.replace(leftSide, rightIdx); // now left points to wherever right is in the heap
        }
    }

    private void reassignRefInStack(String leftSide, int newIndex) {
        if (stackMemory.isEmpty()) throw new IllegalArgumentException("Variable not found");

        Map<String, Integer> map = stackMemory.pop();
        if (map.containsKey(leftSide)) {
            map.replace(leftSide, newIndex);
        } else {
            reassignRefInStack(leftSide, newIndex);
        }
        stackMemory.push(map);
    }

    public void reassignInt(String leftSide, int value, boolean isRef) {
        // handles cases like a = 10; a = 11;
        // assuming variable exists
        Map<String, Integer> leftMap;
        // first, check if local has left variable
        if (currentStack.containsKey(leftSide)) {
            leftMap = currentStack;
            if (isRef) {
                int idx = leftMap.get(leftSide);
                heapMemory.set(idx, value);
            } else {
                leftMap.replace(leftSide, value);
            }
        } else if (checkEntireStack(leftSide)) {
            reassignIntInStack(leftSide, value, isRef);
        } else {
            // if local doesnt have it, must be somewhere in global
            leftMap = staticMemory;
            if (isRef) {
                int idx = leftMap.get(leftSide);
                // check if value in heap is null
                if (heapMemory.get(idx) == NULLVAL) {
                    System.out.println("ERROR: cannot assign values to null variables.");
                    System.exit(-1);
                }
                heapMemory.set(idx, value);
            } else {
                leftMap.replace(leftSide, value);
            }
        }
    }

    private void reassignIntInStack(String variable, int value, boolean isRef) {
        if (stackMemory.isEmpty()) throw new IllegalArgumentException("Variable not found");

        Map<String, Integer> map = stackMemory.pop();
        if (map.containsKey(variable)) {
            if (isRef) {
                int idx = map.get(variable);
                heapMemory.set(idx, value);
            } else {
                map.replace(variable, value);
            }
        } else {
            reassignIntInStack(variable, value, isRef);
        }
        stackMemory.push(map);
    }

    public void reassignRefToInt(String leftSide, int value) {
        // called in case of x = 10; a = new; a = x;
        // called in case of id = ref id type assignment

        Map<String, Integer> leftMap;
        // check if local has left variable
        if (currentStack.containsKey(leftSide)) {
            leftMap = currentStack;

            // index of right side variable
            int idx = leftMap.get(leftSide);
            heapMemory.set(idx, value);
        } else if (checkEntireStack(leftSide)) {
            reassignRefToIntInStack(leftSide, value);
        } else {
            // if local doesnt have it, must be in global
            leftMap = staticMemory;

            // index of right side variable
            int idx = leftMap.get(leftSide);
            heapMemory.set(idx, value);
        }
    }

    private void reassignRefToIntInStack(String leftSide, int value) {
        if (stackMemory.isEmpty()) throw new IllegalArgumentException("Variable not found");

        Map<String, Integer> map = stackMemory.pop();
        if (map.containsKey(leftSide)) {
            int idx = map.get(leftSide);
            heapMemory.set(idx, value);
        } else {
            reassignRefToIntInStack(leftSide, value);
        }
        stackMemory.push(map);
    }

    public void reassignIntToRef(String leftSide, String rightSide) {
        // called in case of x = 10; b = new; x = b;
        Map<String, Integer> leftMap, rightMap;

        // index of right side variable
        int rightIdx;
        
        // check if local has right variable
        if (currentStack.containsKey(rightSide)) {
            rightMap = currentStack;
            rightIdx = rightMap.get(rightSide);
        } else if (checkEntireStack(rightSide)) {
            rightIdx = valueInStack(rightSide);
        } else {
            // if local doesnt have it, must be in global
            rightMap = staticMemory;
            rightIdx = rightMap.get(rightSide);
        }

        // check if local has left variable
        if (currentStack.containsKey(leftSide)) {
            leftMap = currentStack;
        } else {
            // if local doesnt have it, store to global
            leftMap = staticMemory;
        }
        if (checkEntireStack(leftSide)) {
            reassignIntToRefInStack(leftSide, rightIdx);
        } else {
            leftMap.replace(leftSide, rightIdx);
        }
        
        // leftside is now a ref so add to ref set
        currentRefs.add(leftSide);
    }

    private void reassignIntToRefInStack(String leftSide, int value) {
        if (stackMemory.isEmpty()) throw new IllegalArgumentException("Variable not found");

        Map<String, Integer> map = stackMemory.pop();
        if (map.containsKey(leftSide)) {
            map.replace(leftSide, value);
        } else {
            reassignIntToRefInStack(leftSide, value);
        }
        stackMemory.push(map);
    }

    public int getVariableValue(String variable) {
        Map<String, Integer> map = currentStack;
        
        int val;
        if (map.containsKey(variable)) {
            map = currentStack;
            val = map.get(variable);
        } else if (checkEntireStack(variable)) {
            val = valueInStack(variable);
        } else {
            // if variable doesnt exist in local, must be in global
            map = staticMemory;
            val = map.get(variable);
        }

        boolean isRef = currentRefs.contains(variable) || searchRefInStack(variable);
        if (isRef) {
            val = heapMemory.get(val);
        }
        return val;
    }
}
