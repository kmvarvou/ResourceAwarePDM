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
public class ResourceSchedulerMeaningless {
    
     public static List<Pair<Operation,Resource>>  ResourceSchedulerMeaningless( PDM pdm, LinkedHashSet<Operation> executable,  LinkedHashSet<DataElement> available, Heuristic heuristic, HashMap<Operation, Boolean> prerun, Integer instance, double current_time, HashMap<DataElement,Double> executed)
     {
         List<Pair<Operation,Resource>> forExecution = new ArrayList<>();
         LinkedHashSet<Operation> prune = new LinkedHashSet<>();
         
         
         while (pdm.getResourceCapacity(current_time)) {
                        Iterator it = executed.entrySet().iterator();
                        while(it.hasNext())
                        {
                            Map.Entry<DataElement,Double> entry = (Map.Entry<DataElement,Double>) it.next();
                           // System.out.println(entry.getKey() + " , " + entry.getValue() + " , " + current_time);
                        }
             
                        LinkedHashSet<DataElement> available_filtered = new LinkedHashSet<DataElement>();
                        for(DataElement d : available)
                        {
                         
                         
                         
                          if(executed.containsKey(d))
                          {
                           if(executed.get(d)<=current_time)
                           {
                            available_filtered.add(d);
                            //System.out.println(d.getName() + " available_filtered  " +  current_time);
                           }
                          }
                          
                        // System.out.println(available_filtered.size() + "," + "boar");
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
                        
                        for(Operation op : prune)
                        {
                            executable__first_filtered.remove(op);
                        }
                        
                        LinkedHashSet<Operation> executable_filtered = pdm.executableFilter(executable__first_filtered, current_time);

                        if (executable_filtered.isEmpty()) {
                            //System.out.println("execution finished");
                            
                            //System.out.println();
                            //sum_cost[i] += cost;
                            //sum_time[i] += time;
                            //obj_runs += 1;

                            break;
                        }

                        Pair<Operation,Resource> nextOp_pair = heuristic.execute(pdm, executable_filtered, available_filtered, prerun, current_time);
                        nextOp_pair.getFirst().setStartingTime(current_time);
                        Operation nextOp = nextOp_pair.getFirst();
                        Resource nextOp_resource = nextOp_pair.getSecond();
                        // nextOp can never be null
                        //nextOp = heuristic.execute(pdm, executable_filtered, available, prerun, current_time);
                       
                        forExecution.add(nextOp_pair);
                        if(!prerun.containsKey(nextOp))
                        {
                           LinkedHashSet<Operation> nextOp_prune = prune(pdm.operations(),nextOp);
                           for(Operation op : nextOp_prune)
                           {
                               prune.add(op);
                               executable_filtered.remove(op);
                           }
                        }
                        
                        
                       nextOp_resource.assignResource(instance, current_time, (current_time + nextOp.getTime(nextOp_resource)));
                        pdm.operations().remove(nextOp);
                        if (available.contains(pdm.target())) {
                           
                            break;
                        }
                        

                        
                        
                        


                    }
         
         
         System.out.println("end of scheduling: " + forExecution.size() + "  , " + pdm.operations().size());
         return forExecution;
         
         
     }
     
     
     
     
      private static LinkedHashSet<Operation> prune(LinkedHashSet<Operation> operations, Operation operation)
    {
        int count=0;
        LinkedHashSet<Operation> prune = new LinkedHashSet<Operation>();
        for(Operation op : operations)
        {
           if(op.output().equals(operation.output()))
           {
             count +=1;  
           }
        }
        
        
        //Iterator it = operations.entrySet().iterator();
        //String[] operation = (String[])operations.get(key);
        //String output = operation[operation.length-4];
        //int count=0;
        
        LinkedHashSet<DataElement> candidates = new LinkedHashSet<>();
        LinkedHashSet<DataElement> prune2 = new LinkedHashSet<>();
        
        for(Operation op : operations)
        {
            if(op.input().contains(operation.output()))
            {
                for(DataElement cand : op.input())
                {
                    if(!cand.equals(operation.output()))
                    {
                        candidates.add(cand);
                    }
                }
            }
        }
         
         
         
        
        
        int sum_cand=0;
        int sum_out=0;
        for(DataElement cand : candidates)
        {
          //System.out.println(cand);
          sum_cand=0;
          sum_out=0;
          for(Operation op : operations)
          {
              if(op.input().contains(cand) && op.input().contains(operation.output()))
              {
                  sum_out+=1;
              }
              if(op.input().contains(cand) && !op.input().contains(operation.output()))
              {
                  sum_cand+=1;
              }
          }
          
          if(sum_out==sum_cand)
          {
              prune2.add(cand);
          }
        }
        
        for(DataElement data : prune2)
        {
             for(Operation op : operations)
             {
                 if(op.output().equals(data))
                 {
                     prune.add(op);
                 }
             }
            
        }
        
       return prune;
    }
     
  
}