package org.auth.heuristic;

import org.auth.model.DataElement;
import org.auth.model.EdgeWeight;
import org.auth.model.PDM;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;

public abstract class GraphHeuristic implements Heuristic {

    protected Graph<DataElement, DefaultWeightedEdge> graph;
    protected HashMap<DataElement, Double> distances;

    public GraphHeuristic(PDM pdm, EdgeWeight edgeWeight) {
        // create graph
        graph = pdm.graph(edgeWeight);

        // find all paths
        DijkstraShortestPath<DataElement, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<DataElement, DefaultWeightedEdge> paths = dijkstra.getPaths(pdm.target());

        // create a distance index
        distances = new HashMap<>();
        for (DataElement element : pdm.dataElements()) {
            distances.put(element, paths.getWeight(element));
        }
    }


}
