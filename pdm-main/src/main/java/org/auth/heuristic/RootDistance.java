package org.auth.heuristic;

import org.auth.model.DataElement;
import org.auth.model.EdgeWeight;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.util.Utilities;

import java.util.HashMap;
import java.util.LinkedHashSet;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

public class RootDistance extends GraphHeuristic {

    public RootDistance(PDM pdm) {
        super(pdm, new EdgeWeight() {
            @Override
            public double get(Operation operation) {
                return 1.0;
            }
        });
    }
    
    @Override
    public Pair<Operation,Resource> execute(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Operation operation = Utilities.getOperationWithMinOutputDistance(executable, distances);
        Resource resource = operation.getRandomResource(current_time);
        //Resource resource = operation.getLowestCostResource(current_time);
        Pair <Operation,Resource> result = new Pair<Operation,Resource>(operation,resource);
        executable.remove(operation);
        if (prerun.get(operation)) {
            //available.add(operation.output());
           // System.out.println(operation.getName() + "," +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName());
        } else {
           // System.out.println(operation.getName() + "," +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName() + "  fail");
        }
        return result;
    }


    @Override
    public Pair<Operation,Resource> execute_nr(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Operation operation = Utilities.getOperationWithMinOutputDistance(executable, distances);
        executable.remove(operation);
        Pair<Operation,Resource> result;
        if (prerun.get(operation)) {
            //available.add(operation.output());
            System.out.println(operation.getName() + "," + operation.getTime() + ", resource:" + operation.getDefaultResource().getName());
        } else {
            System.out.println(operation.getName() + "," + operation.getTime() + ", resource:" + operation.getDefaultResource().getName() + "  fail");
        }
        
        result = new Pair(operation,operation.getDefaultResource());
        return result;
    }
}
