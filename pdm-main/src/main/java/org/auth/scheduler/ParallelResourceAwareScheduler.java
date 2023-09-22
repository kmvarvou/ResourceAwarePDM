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

public class ParallelResourceAwareScheduler implements Scheduler
{
   public static List<Pair<Operation,Resource>> ParallelResourceAwareScheduler( PDM pdm, LinkedHashSet<Operation> executable,  LinkedHashSet<DataElement> available, Heuristic heuristic, HashMap<Operation, Boolean> prerun, Integer instance, double current_time, HashMap<DataElement,Double> executed)
     {
         List<Pair<Operation,Resource>> forExecution = new ArrayList<>();
         
         Boolean pass = true;
         
         
         while (!executable.isEmpty()) {
             //System.out.println("current_sizes:" + forExecution.size() + ","  + executable.size());
             
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
             
             
             
             if(!pdm.getResourceCapacity(current_time))
             {
                 current_time = pdm.getNextTime(current_time, pass);
                 System.out.println("next current_time: " + current_time);
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
                       
                        while(executable_filtered.isEmpty())
                        {
                            pass = false;
                            System.out.println("exo idea" + current_time);
                            current_time = pdm.getNextTime(current_time, pass);
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
                            for (Operation op : pdm.operations()) { // for each operation
                            if (!op.hasInput()) { // operation has no input
                               executable__first_filtered.add(op);
                            } else {
                                if (available_filtered.containsAll(op.input())) {
                                    executable__first_filtered.add(op);
                                }
                            }
                        }
                            
                            executable_filtered = pdm.executableFilter(executable__first_filtered, current_time);
                            //System.out.println(executable);
                            //System.out.println("pass current_time: " + current_time);
                        }
         
                         Pair<Operation,Resource> nextOp_pair = heuristic.execute(pdm, executable_filtered, available_filtered, prerun, current_time);
                        
                        
                        Operation nextOp = nextOp_pair.getFirst();
                        
                        Resource nextOp_resource = nextOp_pair.getSecond();
                        nextOp.setStartingTime(current_time);
                        
                        // nextOp can never be null
                        //nextOp = heuristic.execute(pdm, executable_filtered, available, prerun, current_time);
                       
                        forExecution.add(nextOp_pair);
                        System.out.println(nextOp.getName());
                        System.out.println(nextOp.getResources().get(0).getName());
                        System.out.println(nextOp.getResources().size());
                        
                        //System.out.println(nextOp_resource.getName() + "edo???");
                        
                        //Iterator it = nextOp.getTimeResource().entrySet().iterator();
                        
                        executable.remove(nextOp);
                        //nextOp.getResource().assignResource(instance, current_time, current_time + nextOp.getTime());
                        
                        
                        nextOp_resource.assignResource(instance, current_time, current_time + nextOp.getTime(nextOp_resource));
                        pdm.operations().remove(nextOp);
                        
                        
                        
                        if (prerun.get(nextOp) && pdm.target().equals(nextOp)) {
                            
                           return forExecution;
                        }
                        

                        
                        System.out.println( forExecution.size() + " , " + executable.size() + "," + current_time  + "boar");
                        


                    }
         
         
         System.out.println("end of scheduling: " + forExecution.size() + "  , " + pdm.operations().size());
         
         return forExecution;
         
         
     }
     
}
