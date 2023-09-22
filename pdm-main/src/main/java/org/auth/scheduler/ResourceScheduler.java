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
                           // System.out.println(entry.getKey() + " , " + entry.getValue() + " , " + current_time);
                        }
             
                        LinkedHashSet<DataElement> available_filtered = new LinkedHashSet<>();
                        for(DataElement d : available)
                        {
                         
                         
                         
                          if(executed.containsKey(d))
                          {
                           if(executed.get(d)<=current_time)
                           {
                            available_filtered.add(d);
                           // System.out.println(d.getName() + " available_filtered  " +  current_time);
                           }
                          }
                          
                         
                        }
                         
                          LinkedHashSet<Operation> executable__first_filtered = new LinkedHashSet<>();
                        for (Operation op : pdm.operations()) { // for each operation
                            if (!op.hasInput()) { // operation has no input
                                executable__first_filtered.add(op);
                               // System.out.println(op.getName() + " hugo" + available_filtered );
                            } else {
                                if (available_filtered.containsAll(op.input())) {
                                    //System.out.println(op.getName() + " hugo" + available_filtered );
                                    executable__first_filtered.add(op);
                                }
                            }
                        }
                        
                       //System.out.println(executable);
                       //System.out.println("endiameso");
                        LinkedHashSet<Operation> executable_filtered = pdm.executableFilter(executable__first_filtered, current_time);
                        
                        //System.out.println(executable_filtered);
                        //System.out.println(pdm.getAvailableResources(current_time).size() + " vasilis");
                         
                        
                        
                        if (executable_filtered.isEmpty()) {
                            //System.out.println("execution finished");
                            
                            //System.out.println();
                            //sum_cost[i] += cost;
                            //sum_time[i] += time;
                            //obj_runs += 1;

                            return forExecution;
                        }
                        //System.out.println(executable_filtered);
                        Pair<Operation,Resource> nextOp_pair = heuristic.execute(pdm, executable_filtered, available_filtered, prerun, current_time);
                        nextOp_pair.getFirst().setStartingTime(current_time);
                        Operation nextOp = nextOp_pair.getFirst();
                        Resource nextOp_resource = nextOp_pair.getSecond();
                        // nextOp can never be null
                        //nextOp = heuristic.execute(pdm, executable_filtered, available, prerun, current_time);
                      
                        forExecution.add(nextOp_pair);
                        //System.out.println(nextOp.getName() + " , " + nextOp_resource.getName());
                        executable.remove(nextOp);
                        //nextOp.getResource().assignResource(instance, current_time, current_time + nextOp.getTime());
                        nextOp_resource.assignResource(instance, current_time, current_time + nextOp.getTime(nextOp_resource));
                        pdm.operations().remove(nextOp);
                        if (prerun.get(nextOp) && pdm.target().equals(nextOp)) {
                            
                           return forExecution;
                        }
                        

                        
                        
                        


                    }
         
         
         //System.out.println(executable);
         return forExecution;
         
         
     }
     
     
    
}
