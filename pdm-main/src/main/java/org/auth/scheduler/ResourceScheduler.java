/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.auth.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
public class ResourceScheduler {
    
     public static List<Pair<Operation,Resource>> ResourceScheduler( PDM pdm, LinkedHashSet<Operation> executable,  LinkedHashSet<DataElement> available, Heuristic heuristic, HashMap<Operation, Boolean> prerun, Integer instance, double current_time, HashMap<DataElement,Double> executed)
     {
         List<Pair<Operation,Resource>> forExecution = new ArrayList<>();
          
          Boolean pass = true;
        
         
         while (pdm.getResourceCapacity(current_time)) {
                        
                        
                        Iterator it = executed.entrySet().iterator();
                        while(it.hasNext())
                        {
                            Map.Entry<DataElement,Double> entry = (Map.Entry<DataElement,Double>) it.next();
                           
                        }
             
                        LinkedHashSet<DataElement> available_filtered = new LinkedHashSet<>();
                        for(DataElement d : available)
                        {
                         
                         
                         
                          if(executed.containsKey(d))
                          {
                           if(executed.get(d)<=current_time)
                           {
                            available_filtered.add(d);
                           
                           }
                          }
                          
                         
                        }
                         
                          LinkedHashSet<Operation> executable__first_filtered = new LinkedHashSet<>();
                        for (Operation op : pdm.operations()) { // for each operation
                            if (!op.hasInput()) { // operation has no input
                                executable__first_filtered.add(op);
                               
                            } else {
                                if (available_filtered.containsAll(op.input())) {
                                   
                                    executable__first_filtered.add(op);
                                }
                            }
                        }
                        
                       
                        LinkedHashSet<Operation> executable_filtered = pdm.executableFilter(executable__first_filtered, current_time);
                        
                        
                         
                        
                        
                        if (executable_filtered.isEmpty()) {
                            

                            return forExecution;
                        }
                       
                        Pair<Operation,Resource> nextOp_pair = heuristic.execute(pdm, executable_filtered, available_filtered, prerun, current_time);
                        nextOp_pair.getFirst().setStartingTime(current_time);
                        Operation nextOp = nextOp_pair.getFirst();
                        Resource nextOp_resource = nextOp_pair.getSecond();
                        // nextOp can never be null
                        
                      
                        forExecution.add(nextOp_pair);
                       
                        executable.remove(nextOp);
                       
                        nextOp_resource.assignResource(instance, current_time, current_time + nextOp.getTime(nextOp_resource));
                        pdm.operations().remove(nextOp);
                        if (prerun.get(nextOp) && pdm.target().equals(nextOp)) {
                            
                           return forExecution;
                        }
                        

                        
                        
                        


                    }
         
         
         
         return forExecution;
         
         
     }
     
     
    
}
