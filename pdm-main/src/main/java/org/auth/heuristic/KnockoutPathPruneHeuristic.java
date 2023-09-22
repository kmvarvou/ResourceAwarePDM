package org.auth.heuristic;

import org.auth.model.DataElement;
import org.auth.model.EdgeWeight;
import org.auth.model.Operation;
import org.auth.model.PDM;
import org.auth.util.Utilities;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.LinkedHashSet;
import org.auth.model.Resource;
import org.jgrapht.alg.util.Pair;

public abstract class KnockoutPathPruneHeuristic implements Heuristic {

    protected Graph<DataElement, DefaultWeightedEdge> costGraph;
    protected HashMap<DataElement, Double> costDistances;

    protected Graph<DataElement, DefaultWeightedEdge> probGraph;
    protected HashMap<DataElement, Double> probDistances;

    protected HashMap<DataElement, Double> distances;

    public KnockoutPathPruneHeuristic(PDM pdm, EdgeWeight firstWeight, EdgeWeight secondWeight) {
        // create cost graph
        costGraph = pdm.graph(firstWeight);

        
        // find all paths in cost graph
        DijkstraShortestPath<DataElement, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(costGraph);
        ShortestPathAlgorithm.SingleSourcePaths<DataElement, DefaultWeightedEdge> paths = dijkstra.getPaths(pdm.target());

        // create a distance index for cost graph
        costDistances = new HashMap<>();
        for (DataElement element : pdm.dataElements()) {
            costDistances.put(element, paths.getWeight(element));
        }

        // create probability graph
        probGraph = pdm.graph(secondWeight);

        // find all paths in probability graph
        dijkstra = new DijkstraShortestPath<>(probGraph);
        paths = dijkstra.getPaths(pdm.target());

        probDistances = new HashMap<>();

        for (DataElement el : pdm.dataElements()) {
            probDistances.put(el, 1.0);

            GraphPath<DataElement, DefaultWeightedEdge> graphPath =  paths.getPath(el);
            for (DefaultWeightedEdge edge : graphPath.getEdgeList()) {
                probDistances.put(el, probDistances.get(el) * (1 - probGraph.getEdgeWeight(edge)));
            }
        }
        
        
        HashMap<DataElement, Double> costDistances2 = (HashMap<DataElement, Double>) costDistances.clone();
        
        
        
        for(Operation operation : pdm.operations())
        {
            prune2(pdm.operations(),operation,costDistances,costDistances2);
        }
        
        
        
        
        // create a distance index for probability graph
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
    }

   /* public Operation execute(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Operation operation = Utilities.getOperationWithMaxOutputDistance(executable, distances);
        executable.remove(operation);
        if (prerun.get(operation)) {
            //available.add(operation.output());
            System.out.println(operation.getName() + "," + operation.getTime() + ", resource:" + operation.getAvailableResource(current_time).getName());
        } else {
            System.out.println(operation.getName() + "," + operation.getTime() + ", resource:" + operation.getAvailableResource(current_time).getName() + "  fail");
        }
        return operation;
    }*/
    
    @Override
    public Pair<Operation,Resource> execute_nr(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Operation operation = Utilities.getOperationWithMaxOutputDistance(executable, distances);
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
              //System.out.println(two);
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
        if(prune2.isEmpty())
        {
            //System.out.println("na doume");
        }
        
        
       
    }
    
    
    
}
