package org.auth.heuristic;

import org.auth.model.DataElement;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.util.Utilities;

import java.util.HashMap;
import java.util.LinkedHashSet;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

public class Random implements Heuristic {

    
    public Pair<Operation, Resource> execute(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        
        int idx = new java.util.Random().nextInt(executable.size());
        int i = 0;
        Pair<Operation, Resource> result;
        for (Operation op : executable) {
            if (i == idx) {
                executable.remove(op);
                if (prerun.get(op)) {
                   // available.add(op.output());
                  //  System.out.println(op.getName() + "," + current_time + " - " + (current_time +op.getTime()) + ", resource:" + op.getLowestCostResource(current_time).getName() );
                } else {
                   // System.out.println(op.getName() + "," + current_time + " - " + (current_time +op.getTime()) + ", resource:" + op.getLowestCostResource(current_time) + "  fail");
                }
                
                //result = new Pair(op,op.getRandomResource(current_time));
                result = new Pair(op,op.getLowestCostResource(current_time));
                return result;
            }
            i++;
        }

        return null;
    }
    
    
    @Override
    public Pair<Operation,Resource> execute_nr(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        System.out.println(executable.size() + "executable size");
        int idx = new java.util.Random().nextInt(executable.size());
        int i = 0;
        Pair<Operation, Resource> result;
        for (Operation op : executable) {
            if (i == idx) {
                executable.remove(op);
                if (prerun.get(op)) {
                   // available.add(op.output());
                    System.out.println(op.getName() + ","   );
                } else {
                    System.out.println(op.getName() + "  fail");
                }
                result = new Pair(op,op.getDefaultResource());
                return result;
            }
            i++;
        }

        return null;
    }
}
