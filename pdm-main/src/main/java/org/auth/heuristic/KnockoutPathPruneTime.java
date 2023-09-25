package org.auth.heuristic;

import java.util.HashMap;
import java.util.LinkedHashSet;
import org.auth.model.DataElement;
import org.auth.model.EdgeWeight;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.model.Resource;
import org.auth.util.Utilities;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;

public class KnockoutPathPruneTime extends KnockoutPathPruneHeuristic {
    public KnockoutPathPruneTime(PDM pdm) {
        super(pdm, new EdgeWeight() {
            @Override
            public double get(Operation operation) {
                return operation.getTime();
            }
        }, new EdgeWeight() {
            @Override
            public double get(Operation operation) {
                return 1 - operation.getProbability();
            }
        });
    }
    
    @Override
     public Pair<Operation,Resource> execute(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
         costGraph = pdm.graph(new EdgeWeight() {
            @Override
            public double get(Operation operation) {
                return operation.getLowestTime(current_time);
            }});
         
        DijkstraShortestPath<DataElement, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(costGraph);
        ShortestPathAlgorithm.SingleSourcePaths<DataElement, DefaultWeightedEdge> paths = dijkstra.getPaths(pdm.target());

        // create a distance index for cost graph
        costDistances = new HashMap<>();
        for (DataElement element : pdm.dataElements()) {
            costDistances.put(element, paths.getWeight(element));
        }
        
        HashMap<DataElement, Double> costDistances2 = (HashMap<DataElement, Double>) costDistances.clone();
        
        
       
        
        
        for(Operation operation : pdm.operations())
        {
            prune2(pdm.operations(),operation,costDistances,costDistances2);
        }
        
        
        
        distances = new HashMap<>();
        for (DataElement element : pdm.dataElements()) {
            double denominator = costDistances2.get(element);
            double numerator = probDistances.get(element);
            if (denominator != 0) {
                distances.put(element, numerator / denominator);
            } else {
                
               distances.put(element,numerator);
            }
        }
         
         
         
        Operation operation = Utilities.getOperationWithMaxOutputDistance(executable, distances);
        executable.remove(operation);
        Resource resource = operation.getLowestTimeResource(current_time);
        if (prerun.get(operation)) {
            //available.add(operation.output());
           // System.out.println(operation.getName() +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName());
        } else {
           // System.out.println(operation.getName() +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName() + "  fail");
        }
        Pair<Operation,Resource> result = new Pair<>(operation,resource);
        return result;
    }
     
    private static void prune2(LinkedHashSet<Operation> operations, Operation operation,HashMap<DataElement, Double> costDistances, HashMap<DataElement, Double> costDistances2)
    {
       
        
        
        
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
          
          sum_cand=0;
          sum_out=0;
          for(Operation op : operations)
          {
              if(op.input().contains(cand) ) //
              {
                  sum_cand+=1;
              }
              if(op.input().contains(operation.output())  && op.input().contains(cand))
              {
                  sum_out+=1;
              }
          }
          
          if(sum_out==sum_cand)
          {
              prune2.add(cand);
              //String output2 = cand.replace("i","");
              int two = cand.getId();
              
              /*if(dist[0][operation.getId()]!=0) // na to do poio value antiprosoeuei tin ellipsi edge
              {
              dist2[two]+=dist[0][operation.getId()];
              }
              else
              {
              dist2[two]+=operation.getCost();
              }*/
              
              Double oldCost = costDistances2.get(cand);
              Operation candidate_op=null;
              for(Operation op : operations)
              {
               if(op.output().equals(cand))
               {
                if(!op.hasInput())
                {
                    candidate_op = op;
                }
               }
              }
                 if(candidate_op!=null)
                 {
                 oldCost += candidate_op.getCost();
                 
                 }
              
                costDistances2.put(cand, oldCost);
              
              
              
          }
        }
        
        
        
       
    }
    
    
}
