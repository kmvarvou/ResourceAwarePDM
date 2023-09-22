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

public class KnockoutPath extends KnockoutPathHeuristic {
    public KnockoutPath(PDM pdm) {
        super(pdm, new EdgeWeight() {
            @Override
            public double get(Operation operation) {
                return operation.getCost();
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
                return operation.getLowestCost(current_time);
            }});
         
        DijkstraShortestPath<DataElement, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(costGraph);
        ShortestPathAlgorithm.SingleSourcePaths<DataElement, DefaultWeightedEdge> paths = dijkstra.getPaths(pdm.target());

        // create a distance index for cost graph
        costDistances = new HashMap<>();
        for (DataElement element : pdm.dataElements()) {
            costDistances.put(element, paths.getWeight(element));
        }
        
        distances = new HashMap<>();
        for (DataElement element : pdm.dataElements()) {
            double denominator = costDistances.get(element);
            double numerator = probDistances.get(element);
            if (denominator != 0) {
                distances.put(element, numerator / denominator);
            } else {
                distances.put(element,numerator);
            }
        }
         
         Operation operation = Utilities.getOperationWithMaxOutputDistance(executable, distances);
        executable.remove(operation);
        Resource resource = operation.getRandomResource(current_time);
        if (prerun.get(operation)) {
            //available.add(operation.output());
           // System.out.println(operation.getName() +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName());
        } else {
            //System.out.println(operation.getName() +current_time + " - " + (current_time +operation.getTime(resource)) + ", resource:" + resource.getName() + "  fail");
        }
        Pair<Operation,Resource> result = new Pair<>(operation,resource);
        return result;
    }
}
