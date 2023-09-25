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

public abstract class KnockoutPathHeuristic implements Heuristic {

    protected Graph<DataElement, DefaultWeightedEdge> costGraph;
    protected HashMap<DataElement, Double> costDistances;

    protected Graph<DataElement, DefaultWeightedEdge> probGraph;
    protected HashMap<DataElement, Double> probDistances;

    protected HashMap<DataElement, Double> distances;

    public KnockoutPathHeuristic(PDM pdm, EdgeWeight firstWeight, EdgeWeight secondWeight) {
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

        




// create a distance index for probability graph
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
        
        
    }
    
    @Override
    public Pair<Operation,Resource> execute_nr(PDM pdm, LinkedHashSet<Operation> executable, LinkedHashSet<DataElement> available, HashMap<Operation, Boolean> prerun, Double current_time) {
        Operation operation = Utilities.getOperationWithMaxOutputDistance(executable, distances);
        executable.remove(operation);
        Pair<Operation,Resource> result;
        if (prerun.get(operation)) {
            //available.add(operation.output());
            //System.out.println(operation.getName() + "," + operation.getTime() + ", resource:" + operation.getDefaultResource().getName());
        } else {
            //System.out.println(operation.getName() + "," + operation.getTime() + ", resource:" + operation.getDefaultResource().getName() + "  fail");
        }
        
        Resource result_resource;
        if(operation.getDefaultResource()==null)
        {
            result_resource = pdm.defaultResource();
        }
        result = new Pair(operation,pdm.defaultResource());
        return result;
    }
    
    
}
