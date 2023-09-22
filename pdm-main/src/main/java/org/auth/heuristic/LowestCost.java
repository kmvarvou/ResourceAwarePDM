package org.auth.heuristic;

import org.auth.model.DataElement;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.util.Utilities;

import java.util.HashMap;
import java.util.LinkedHashSet;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

public class LowestCost implements Heuristic {

    @Override
    public Pair<Operation,Resource> execute(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Pair<Operation,Resource> pair = Utilities.getOperationWithMinCost(executable, current_time);
        Operation operation = pair.getFirst();
        Resource resource = pair.getSecond();
        executable.remove(operation);
        System.out.println(operation.getId());
        if (prerun.get(operation)) {
            //available.add(operation.output());
            //System.out.println(operation.getName()+ "," +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName());
        } else {
            // log failure
            //System.out.println(operation.getName()+ "," +current_time + " - " + (current_time +operation.getTime(resource))  + ", resource:" + resource.getName() + "  fail");
        }
        
        return pair;
    }
    
    
    public Pair<Operation,Resource> execute_nr(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Operation operation = Utilities.getOperationWithMinCost(executable);
        executable.remove(operation);
        Pair<Operation,Resource> result;
        if (prerun.get(operation)) {
            //available.add(operation.output());
            System.out.println(operation.getName()+ "," + operation.getTime());
        } else {
            // log failure
            System.out.println(operation.getName()+ "," + operation.getTime() + "  fail");
        }
        
        result = new Pair(operation,operation.getDefaultResource());
        return result;
    }
}
