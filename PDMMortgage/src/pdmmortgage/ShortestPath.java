/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdmmortgage;

/**
 *
 * @author kostis
 */
import java.util.*; 
import java.lang.*; 
import java.io.*; 
  
public class ShortestPath { 
    // A utility function to find the vertex with minimum distance value, 
    // from the set of vertices not yet included in shortest path tree 
     
    int minDistance(int dist[], Boolean sptSet[]) 
    { 
        // Initialize min value 
        int min = Integer.MAX_VALUE, min_index = -1; 
  
        for (int v = 0; v < dist.length; v++) 
            if (sptSet[v] == false && dist[v] <= min) { 
                min = dist[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    } 
  
    // A utility function to print the constructed distance array 
    void printSolution(int dist[], int n) 
    { 
        System.out.println("Vertex   Distance from Source"); 
        for (int i = 0; i < dist.length; i++) 
            System.out.println(i + " tt " + dist[i]); 
    } 
  
    // Function that implements Dijkstra's single source shortest path 
    // algorithm for a graph represented using adjacency matrix 
    // representation 
    int[] dijkstra(int graph[][], int src) 
    { 
        int dist[] = new int[graph.length]; // The output array. dist[i] will hold 
        // the shortest distance from src to i 
  
        // sptSet[i] will true if vertex i is included in shortest 
        // path tree or shortest distance from src to i is finalized 
        Boolean sptSet[] = new Boolean[graph.length]; 
  
        // Initialize all distances as INFINITE and stpSet[] as false 
        for (int i = 0; i < graph.length; i++) { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        } 
  
        // Distance of source vertex from itself is always 0 
        dist[src] = 0; 
  
        // Find shortest path for all vertices 
        for (int count = 0; count < graph.length - 1; count++) { 
            // Pick the minimum distance vertex from the set of vertices 
            // not yet processed. u is always equal to src in first 
            // iteration. 
            int u = minDistance(dist, sptSet); 
  
            // Mark the picked vertex as processed 
            sptSet[u] = true; 
  
            // Update dist value of the adjacent vertices of the 
            // picked vertex. 
            for (int v = 0; v < graph.length; v++) 
  
                // Update dist[v] only if is not in sptSet, there is an 
                // edge from u to v, and total weight of path from src to 
                // v through u is smaller than current value of dist[v] 
                if (!sptSet[v] && graph[u][v] != -1 &&  
                   dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) 
                    dist[v] = dist[u] + graph[u][v]; 
        } 
  
        // print the constructed distance array 
       //printSolution(dist, V);
       for(int i=0; i<graph.length;i++)
       {
           if(graph[0][i]!=-1)
           {
               dist[i] +=graph[0][i];
           }
       }   
       
        return dist;
    } 
  
    // Driver method 
    
} 