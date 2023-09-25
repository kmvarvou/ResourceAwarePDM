/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.auth.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import org.auth.heuristic.Heuristic;
import org.auth.model.DataElement;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

/**
 *
 * @author kostis
 */
public class OGScheduler {
    
    public static List<Pair<Operation,Resource>> OGScheduler( PDM pdm, LinkedHashSet<Operation> executable,  LinkedHashSet<DataElement> available, Heuristic heuristic, HashMap<Operation, Boolean> prerun, Integer instance, double current_time)
     {
         List<Pair<Operation,Resource>> forExecution = new ArrayList<>();
         
         
         
        
            
                        for (Operation op : pdm.operations()) { // for each operation
                            if (!op.hasInput()) { // operation has no input
                                executable.add(op);
                            } else {
                                if (available.containsAll(op.input())) {
                                    executable.add(op);
                                }
                            }
                        }
                        
                        //LinkedHashSet<Operation> executable_filtered = pdm.executableFilter(executable, current_time);

                        if (executable.isEmpty()) {
                            

                            return forExecution;
                        }

                        Pair<Operation,Resource> nextOp_pair = heuristic.execute_nr(pdm, executable, available, prerun, current_time);
                        nextOp_pair.getFirst().setStartingTime(current_time);
                        Operation nextOp = nextOp_pair.getFirst();
                        Resource nextOp_resource = pdm.defaultResource();
                        // nextOp can never be null
                        //nextOp = heuristic.execute(pdm, executable_filtered, available, prerun, current_time);
                       
                        forExecution.add(nextOp_pair);
                        
                        
                        //nextOp.getResource().assignResource(instance, current_time, current_time + nextOp.getTime());
                        pdm.defaultResource().assignResource(instance, current_time, (current_time + nextOp.getTime(nextOp_resource)));
                        pdm.operations().remove(nextOp);
                        
                        if (available.contains(pdm.target())) {
                           
                            return forExecution;
                        }
                        

                        
                        
                        


                    
         
         
         
         return forExecution;
         
         
     }
    
}
